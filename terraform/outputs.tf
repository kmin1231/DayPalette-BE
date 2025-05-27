# outputs.tf

output "ec2_public_ip" {
  description = "Public IP of the EC2 instance"
  value       = aws_instance.spring_boot_server.public_ip
}

output "rds_endpoint" {
  description = "RDS instance endpoint"
  value       = aws_db_instance.rds_db_instance.endpoint
}

output "ecr_repository_url" {
  description = "ECR repository URL"
  value       = aws_ecr_repository.ecr_repo.repository_url
}

output "s3_bucket_name" {
  description = "S3 bucket name"
  value       = aws_s3_bucket.s3_bucket.bucket
}