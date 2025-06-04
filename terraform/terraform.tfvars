# terraform.tfvars

aws_region = "ap-northeast-2"

# EC2
ami_id = "ami-0eb302fcc77c2f8bd"
instance_type = "t2.micro"
key_name = "daypalette-server-key"

# ECR
ecr_repo_name = "daypalette-spring"

# S3
s3_bucket_name = "daypalette-bucket"

# VPC
vpc_cidr_block = "10.0.0.0/16"