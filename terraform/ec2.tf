# ec2.tf

resource "aws_instance" "spring_boot_server" {
  ami               = var.ami_id
  instance_type     = var.instance_type
  key_name          = var.key_name
  subnet_id         = var.subnet_id
  associate_public_ip_address = true

  tags = {
    Name = "DayPalette_spb"
  }
}

output "ec2_public_ip" {
  value = aws_instance.spring_boot_server.public_ip
}