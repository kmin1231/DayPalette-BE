# outputs.tf

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

output "vpc_id" {
  value = aws_vpc.main.id
}

output "nat_gateway_id" {
  value = aws_nat_gateway.nat_gw.id
}

output "rds_subnet_group_name" {
  description = "Name of RDS subnet group"
  value       = aws_db_subnet_group.rds_subnet_group.name
}

output "alb_dns_name" {
  description = "ALB DNS name"
  value       = aws_lb.app_alb.dns_name
}