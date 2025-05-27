# main.tf

# provider
provider "aws" {
  region     = var.aws_region
  access_key = var.aws_access_key
  secret_key = var.aws_secret_key
}

# ECR
resource "aws_ecr_repository" "ecr_repo" {
  name = var.ecr_repo_name
}

# S3 bucket
resource "aws_s3_bucket" "s3_bucket" {
  bucket = var.s3_bucket_name
}