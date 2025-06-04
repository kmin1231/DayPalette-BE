# network.tf

resource "aws_vpc" "main" {
  cidr_block           = var.vpc_cidr_block
  enable_dns_support   = true
  enable_dns_hostnames = true

  tags = {
    Name = "daypalette-vpc"
  }
}

# Public Subnet (ALB)
resource "aws_subnet" "public_subnet_a" {
  vpc_id                  = aws_vpc.main.id
  cidr_block              = "10.0.1.0/24"
  map_public_ip_on_launch = true
  availability_zone       = "ap-northeast-2a"
  tags = { Name = "daypalette-public-subnet-a" }
}

resource "aws_subnet" "public_subnet_c" {
  vpc_id                  = aws_vpc.main.id
  cidr_block              = "10.0.3.0/24"
  map_public_ip_on_launch = true
  availability_zone       = "ap-northeast-2c"
  tags = { Name = "daypalette-public-subnet-c" }
}

# Private Subnet (EC2, RDS)
resource "aws_subnet" "private_subnet_a" {
  vpc_id            = aws_vpc.main.id
  cidr_block        = "10.0.2.0/24"
  availability_zone = "ap-northeast-2a"
  tags = { Name = "daypalette-private-subnet-a" }
}

resource "aws_subnet" "private_subnet_c" {
  vpc_id            = aws_vpc.main.id
  cidr_block        = "10.0.4.0/24"
  availability_zone = "ap-northeast-2c"
  tags = { Name = "daypalette-private-subnet-c" }
}

# Internet Gateway (IGW)
resource "aws_internet_gateway" "igw" {
  vpc_id = aws_vpc.main.id

  tags = {
    Name = "daypalette-igw"
  }
}

# Public Route Table
resource "aws_route_table" "public_rt" {
  vpc_id = aws_vpc.main.id

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.igw.id
  }

  tags = {
    Name = "daypalette-public-rt"
  }
}

# Public Subnet - Public Route Table
resource "aws_route_table_association" "public_subnet_assoc_a" {
  subnet_id      = aws_subnet.public_subnet_a.id
  route_table_id = aws_route_table.public_rt.id
}

resource "aws_route_table_association" "public_subnet_assoc_c" {
  subnet_id      = aws_subnet.public_subnet_c.id
  route_table_id = aws_route_table.public_rt.id
}

# Elastic IP (NAT Gateway)
resource "aws_eip" "nat_eip" {
  domain = "vpc"
}

# NAT Gateway (Private subnet)
resource "aws_nat_gateway" "nat_gw" {
  allocation_id = aws_eip.nat_eip.id
  subnet_id     = aws_subnet.public_subnet_a.id

  tags = {
    Name = "daypalette-nat-gw"
  }
}

# Private Route Table
resource "aws_route_table" "private_rt" {
  vpc_id = aws_vpc.main.id

  route {
    cidr_block     = "0.0.0.0/0"
    nat_gateway_id = aws_nat_gateway.nat_gw.id
  }

  tags = {
    Name = "daypalette-private-rt"
  }
}

# Private Subnet - Private Route Table
resource "aws_route_table_association" "private_subnet_assoc_a" {
  subnet_id      = aws_subnet.private_subnet_a.id
  route_table_id = aws_route_table.private_rt.id
}

resource "aws_route_table_association" "private_subnet_assoc_c" {
  subnet_id      = aws_subnet.private_subnet_c.id
  route_table_id = aws_route_table.private_rt.id
}

resource "aws_vpc_endpoint" "s3" {
  vpc_id            = aws_vpc.main.id
  service_name      = "com.amazonaws.${var.aws_region}.s3"
  vpc_endpoint_type = "Gateway"

route_table_ids = [
    aws_route_table.public_rt.id,
    aws_route_table.private_rt.id,
  ]

  tags = {
    Name = "S3 VPC Endpoint"
  }
}

resource "aws_vpc_endpoint" "ssm" {
  vpc_id            = aws_vpc.main.id
  service_name      = "com.amazonaws.${var.aws_region}.ssm"
  vpc_endpoint_type = "Interface"
  subnet_ids        = [aws_subnet.private_subnet_a.id, aws_subnet.private_subnet_c.id]
  security_group_ids = [aws_security_group.ec2_sg.id]

  private_dns_enabled = true

    policy = <<POLICY
{
  "Statement": [
    {
      "Effect": "Allow",
      "Principal": "*",
      "Action": [
        "ssm:*"
      ],
      "Resource": "*"
    }
  ]
}
POLICY

  tags = {
    Name = "SSM VPC Endpoint"
  }
}

resource "aws_vpc_endpoint" "ec2messages" {
  vpc_id            = aws_vpc.main.id
  service_name      = "com.amazonaws.${var.aws_region}.ec2messages"
  vpc_endpoint_type = "Interface"
  subnet_ids        = [aws_subnet.private_subnet_a.id, aws_subnet.private_subnet_c.id]
  security_group_ids = [aws_security_group.ec2_sg.id]

  private_dns_enabled = true

    policy = <<POLICY
{
  "Statement": [
    {
      "Effect": "Allow",
      "Principal": "*",
      "Action": [
        "ec2messages:*"
      ],
      "Resource": "*"
    }
  ]
}
POLICY

  tags = {
    Name = "EC2Messages VPC Endpoint"
  }
}

resource "aws_vpc_endpoint" "ssmmessages" {
  vpc_id            = aws_vpc.main.id
  service_name      = "com.amazonaws.${var.aws_region}.ssmmessages"
  vpc_endpoint_type = "Interface"
  subnet_ids        = [aws_subnet.private_subnet_a.id, aws_subnet.private_subnet_c.id]
  security_group_ids = [aws_security_group.ec2_sg.id]

  private_dns_enabled = true

    policy = <<POLICY
{
  "Statement": [
    {
      "Effect": "Allow",
      "Principal": "*",
      "Action": [
        "ssmmessages:*"
      ],
      "Resource": "*"
    }
  ]
}
POLICY

  tags = {
    Name = "SSMMessages VPC Endpoint"
  }
}

resource "aws_security_group_rule" "allow_https_inbound" {
  type              = "ingress"
  from_port         = 443
  to_port           = 443
  protocol          = "tcp"
  security_group_id = aws_security_group.ec2_sg.id
  cidr_blocks       = ["0.0.0.0/0"]
  description       = "Allow HTTPS inbound from anywhere"
}