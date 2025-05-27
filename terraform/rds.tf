# rds.tf

resource "aws_db_instance" "rds_db_instance" {
  identifier                = "db-instance"
  engine                    = "mysql"
  engine_version            = "8.0.37"
  instance_class            = "db.t4g.micro"
  allocated_storage         = 20
  storage_type              = "gp2"
  username                  = var.rds_username
  password                  = var.rds_password
  db_name                   = "daypalette_db"
  db_subnet_group_name      = var.db_subnet_group_name
  vpc_security_group_ids    = var.db_security_group_ids
  publicly_accessible       = true
}