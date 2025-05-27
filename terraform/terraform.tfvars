# terraform.tfvars

aws_region          = "ap-northeast-2"

# EC2
ami_id              = "ami-0eb302fcc77c2f8bd"
instance_type       = "t2.micro"
key_name            = "daypalette-spb-key"
subnet_id           = "subnet-063e4714b5275fe40"

# ECR
ecr_repo_name       = "daypalette-spring"

# S3
s3_bucket_name      = "daypalette-bucket"

# RDS
db_subnet_group_name   = "default-vpc-0056ab2af56b3283a"
db_security_group_ids  = ["sg-02f34f5227a88eeba"]