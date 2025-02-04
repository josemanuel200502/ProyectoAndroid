package com.example.esencias

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class InformacionCurso : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el diseño del fragment
        val view = inflater.inflate(R.layout.fragment_informacion_curso, container, false)

        // Referencias a los elementos de la vista
        val imageCurso: ImageView = view.findViewById(R.id.imageCurso)
        val titleCurso: TextView = view.findViewById(R.id.titleCurso)
        val descriptionCurso: TextView = view.findViewById(R.id.descriptionCurso)
        val infoCurso: TextView = view.findViewById(R.id.infoCurso)
        val buttonReserva: Button = view.findViewById(R.id.buttonReserva)

        // Instancia del helper de la base de datos
        val databaseHelper = BBDD(requireContext())

        // Obtener el producto desde la base de datos
        val productoCurso = databaseHelper.getCurso()

        if (productoCurso != null) {
            // Configurar los datos obtenidos en los elementos de la vista

            Glide.with(imageCurso.context)
                .load(productoCurso.imagen)
                .apply(RequestOptions())
                .into(imageCurso)

            titleCurso.text = productoCurso.nombre
            descriptionCurso.text = productoCurso.descripcion
            infoCurso.text = productoCurso.informacion

            // Acción del botón de reserva
            buttonReserva.setOnClickListener {
                if (productoCurso.plazasDisponibles > 0) {
                    Toast.makeText(
                        requireContext(),
                        "Reserva realizada para el curso: ${productoCurso.nombre}",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Lo sentimos, no hay plazas disponibles.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else {
            // Manejo en caso de que no haya datos del curso
            Toast.makeText(requireContext(), "No se encontraron datos del curso", Toast.LENGTH_SHORT).show()
        }

        return view
    }
}