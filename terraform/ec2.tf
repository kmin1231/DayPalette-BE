# ec2.tf

resource "aws_iam_role" "ec2_admin_role" {
  name = "ec2_admin_role"

  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [{
      Effect = "Allow"
      Principal = {
        Service = "ec2.amazonaws.com"
      }
      Action = "sts:AssumeRole"
    }]
  })
}

resource "aws_iam_role_policy_attachment" "ssm_policy_attach" {
  role       = aws_iam_role.ec2_admin_role.name
  policy_arn = "arn:aws:iam::aws:policy/AmazonSSMManagedInstanceCore"
}

resource "aws_iam_role_policy_attachment" "ec2_full_access_attach" {
  role       = aws_iam_role.ec2_admin_role.name
  policy_arn = "arn:aws:iam::aws:policy/AmazonEC2FullAccess"
}

resource "aws_iam_instance_profile" "ec2_admin_instance_profile" {
  name = "ec2_admin_instance_profile"
  role = aws_iam_role.ec2_admin_role.name
}

resource "aws_security_group" "ec2_sg" {
  name        = "ec2-sg"
  vpc_id      = aws_vpc.main.id

  ingress {
    from_port   = 8090
    to_port     = 8090
    protocol    = "tcp"
    security_groups = [aws_security_group.alb_sg.id] # ALB access only
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "ec2-sg"
  }
}

resource "aws_launch_template" "lt" {
  name_prefix   = "daypalette-lt-"
  image_id      = var.ami_id
  instance_type = var.instance_type
  key_name      = var.key_name

  vpc_security_group_ids = [aws_security_group.ec2_sg.id]

  iam_instance_profile {
    name = aws_iam_instance_profile.ec2_admin_instance_profile.name
  }

  user_data = base64encode(<<-EOF
              #!/bin/bash
              sudo yum update -y
              sudo yum install -y docker amazon-ssm-agent
              sudo systemctl enable docker
              sudo systemctl start docker
              sudo usermod -aG docker ec2-user

              sudo systemctl enable amazon-ssm-agent
              sudo systemctl start amazon-ssm-agent
              EOF
  )

  tag_specifications {
    resource_type = "instance"
    tags = {
      Name = "DayPalette_spb"
    }
  }
}

resource "aws_autoscaling_group" "asg" {
  name                      = "daypalette-asg"
  max_size                  = 1
  min_size                  = 1
  desired_capacity          = 1

  vpc_zone_identifier = [
    aws_subnet.private_subnet_a.id,
    aws_subnet.private_subnet_c.id
  ]

  launch_template {
    id      = aws_launch_template.lt.id
    version = "$Latest"
  }

  target_group_arns = [
    aws_lb_target_group.springboot_tg.arn
  ]

  tag {
    key                 = "Name"
    value               = "DayPalette_spb"
    propagate_at_launch = true
  }

  health_check_type         = "ELB"
  health_check_grace_period = 1200
}