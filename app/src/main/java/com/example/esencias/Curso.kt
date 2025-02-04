package com.example.esencias

data class Curso(
    val idProducto: Int,
    val nombre: String,
    val precio: Double,
    val descripcion: String,
    val informacion: String,
    val imagen: String,
    val plazasDisponibles: Int,
    val plazasMaximas: Int
)