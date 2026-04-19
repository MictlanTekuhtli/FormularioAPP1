package com.MictlanTKL.formulario

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.MictlanTKL.formulario.databinding.ActivityReporteBinding
import com.bumptech.glide.Glide

class ReporteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReporteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReporteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recuperar el perfil enviado desde MainActivity
        val perfil = intent.getParcelableExtra<PerfilUsuario>("perfil_usuario")

        // Mostrar los datos en la UI
        mostrarDatosPerfil(perfil)

        // Configurar el botón para volver al formulario
        binding.btnVolver.setOnClickListener {
            finish() // Cierra esta Activity y regresa a MainActivity
        }
    }

    private fun mostrarDatosPerfil(perfil: PerfilUsuario?) {
        if (perfil == null) {
            // En caso de que no llegue el objeto, mostrar mensaje y deshabilitar vistas
            binding.tvNombreCompleto.text = getString(R.string.error_perfil_no_encontrado)
            return
        }

        // Nombre completo
        binding.tvNombreCompleto.text = "${perfil.nombre} ${perfil.apellidos}"

        // Fecha de nacimiento
        binding.tvFechaNacimiento.text = perfil.fechaNacimiento

        // Género
        binding.tvGenero.text = perfil.genero

        // Teléfono (concatenamos la lada +52)
        binding.tvTelefono.text = "+52 ${perfil.telefono}"

        // Email
        binding.tvEmail.text = perfil.email

        // Intereses: unir con coma o mostrar "Ninguno"
        val interesesTexto = perfil.intereses.joinToString(", ")
        binding.tvIntereses.text = interesesTexto.ifEmpty { getString(R.string.ninguno) }

        // Foto de perfil (si se envió una URI)
        // Nota: Requiere que PerfilUsuario tenga un campo 'fotoUri: String?'
        // Si aún no lo tienes, puedes omitir esta parte o agregar el campo.
        // perfil.fotoUri?.let { uriString ->
        //     val uri = Uri.parse(uriString)
        //     Glide.with(this)
        //         .load(uri)
        //         .placeholder(R.drawable.ic_person_placeholder)
        //         .into(binding.ivFotoReporte)
        // }
    }
}