# rds.tf

resource "aws_db_instance" "rds_db_instance" {
  identifier                = "rds-instance"
  # snapshot_identifier     = "rds-snapshot-250602"
  skip_final_snapshot       = true
  engine                    = "mysql"
  engine_version            = "8.0.37"
  instance_class            = "db.t4g.micro"
  allocated_storage         = 20
  storage_type              = "gp2"
  storage_encrypted         = true
  username                  = var.rds_username
  password                  = var.rds_password
  db_name                   = "daypalette_db"
  db_subnet_group_name      = aws_db_subnet_group.rds_subnet_group.name
  vpc_security_group_ids    = [aws_security_group.rds_sg.id]
  publicly_accessible       = false

  depends_on = [aws_db_subnet_group.rds_subnet_group]

  tags = {
    Name = "rds-db"
  }
}

resource "aws_db_subnet_group" "rds_subnet_group" {
  name       = "daypalette-rds-subnet-group"
  subnet_ids = [
    aws_subnet.private_subnet_a.id,
    aws_subnet.private_subnet_c.id
  ]

  tags = {
    Name = "daypalette-rds-subnet-group"
  }
}

resource "aws_security_group" "rds_sg" {
  name   = "rds-sg"
  vpc_id = aws_vpc.main.id

  ingress {
    from_port   = 3306
    to_port     = 3306
    protocol    = "tcp"
    security_groups = [aws_security_group.ec2_sg.id] # EC2 access only
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "rds-sg"
  }
}