# alb.tf

resource "aws_lb" "app_alb" {
  name               = "daypalette-alb"
  internal           = false
  load_balancer_type = "application"
  subnets = [
    aws_subnet.public_subnet_a.id,
    aws_subnet.public_subnet_c.id
  ]
  
  security_groups    = [aws_security_group.alb_sg.id]

  enable_deletion_protection = false
}

resource "aws_lb_target_group" "springboot_tg" {
  name     = "springboot-tg"
  port     = 8090
  protocol = "HTTP"
  vpc_id   = aws_vpc.main.id

  health_check {
    path                = "/actuator/health"
    protocol            = "HTTP"
    matcher             = "200"
    interval            = 30
    timeout             = 5
    healthy_threshold   = 2
    unhealthy_threshold = 2
  }
}

resource "aws_lb_listener" "alb_listener" {
  load_balancer_arn = aws_lb.app_alb.arn
  port              = 80
  protocol          = "HTTP"

  default_action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.springboot_tg.arn
  }
}

resource "aws_security_group" "alb_sg" {
  name        = "alb-sg"
  description = "Allow HTTP access to ALB"
  vpc_id      = aws_vpc.main.id

  ingress {
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_lb_target_group" "prometheus_tg" {
  name     = "prometheus-tg"
  port     = 9090
  protocol = "HTTP"
  vpc_id   = aws_vpc.main.id

  health_check {
    path                = "/metrics"
    protocol            = "HTTP"
    matcher             = "200"
    interval            = 30
    timeout             = 5
    healthy_threshold   = 2
    unhealthy_threshold = 2
  }
}

resource "aws_lb_target_group" "grafana_tg" {
  name     = "grafana-tg"
  port     = 3000
  protocol = "HTTP"
  vpc_id   = aws_vpc.main.id

  health_check {
    path                = "/"
    protocol            = "HTTP"
    matcher             = "200"
    interval            = 30
    timeout             = 5
    healthy_threshold   = 2
    unhealthy_threshold = 2
  }
}

resource "aws_lb_listener" "prometheus_listener" {
  load_balancer_arn = aws_lb.app_alb.arn
  port              = 9090
  protocol          = "HTTP"

  default_action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.prometheus_tg.arn
  }
}

resource "aws_lb_listener" "grafana_listener" {
  load_balancer_arn = aws_lb.app_alb.arn
  port              = 3000
  protocol          = "HTTP"

  default_action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.grafana_tg.arn
  }
}