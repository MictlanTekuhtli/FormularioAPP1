package com.MictlanTKL.formulario

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.MictlanTKL.formulario.databinding.ActivityMainBinding
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Calendar
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.core.app.ActivityCompat
import android.widget.Toast
import com.bumptech.glide.Glide // Para cargar la imagen fácilmente

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var fotoPerfilUri: Uri? = null
    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            fotoPerfilUri = it
            // Cargar imagen en el CircleImageView (usando Glide)
            Glide.with(this)
                .load(it)
                .placeholder(R.drawable.ic_person_placeholder)
                .into(binding.ivFotoPerfil)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupGenderDropdown()
        setupDatePicker()
        setupCreateProfileButton()
        binding.etEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                binding.tilEmail.error = null
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }


    private fun setupCreateProfileButton() {
        binding.btnCrearPerfil.setOnClickListener {
            if (validarFormulario()) {
                // Recoger todos los datos del formulario
                val perfil = PerfilUsuario(
                    nombre = binding.etNombres.text.toString().trim(),
                    apellidos = binding.etApellidos.text.toString().trim(),
                    fechaNacimiento = binding.etFechaNacimiento.text.toString().trim(),
                    genero = binding.etGenero.text.toString(),
                    telefono = binding.etTelefono.text.toString().trim(),
                    email = binding.etEmail.text.toString().trim(),
                    intereses = obtenerInteresesSeleccionados(),
                    //descripcion = binding.etDescripcion.text.toString().trim().takeIf {it.isNotEmpty()}
                    fotoUri = fotoPerfilUri?.toString()
                )
                val intent = Intent(this, ReporteActivity::class.java)
                intent.putExtra("perfil_usuario", perfil)
                startActivity(intent)
            }
        }
    }
    private fun setupGenderDropdown() {
        val genders = listOf(
            getString(R.string.genero_masculino),
            getString(R.string.genero_femenino),
            getString(R.string.genero_no)
        )
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, genders)
        (binding.tilGenero.editText as? MaterialAutoCompleteTextView)?.setAdapter(adapter)

    }
    @SuppressLint("DefaultLocale")
    private fun setupDatePicker() {
        // Obtener la fecha actual para mostrarla inicialmente en el calendario
        val calendar = Calendar.getInstance()
        val anioActual = calendar.get(Calendar.YEAR)
        val mesActual = calendar.get(Calendar.MONTH)
        val diaActual = calendar.get(Calendar.DAY_OF_MONTH)

        binding.etFechaNacimiento.setOnClickListener {
            // Crear y mostrar el DatePickerDialog
            DatePickerDialog(this, { _, year, month, day ->
                val fechaFormateada = String.format("%02d/%02d/%04d", day, month + 1, year)
                binding.etFechaNacimiento.setText(fechaFormateada)
                // Validar edad inmediatamente y mostrar error si es necesario
                if (!esMayorDe13(fechaFormateada)) {
                    binding.tilFechaNacimiento.error = getString(R.string.error_edad_minima)
                } else {
                    binding.tilFechaNacimiento.error = null
                }
            }, anioActual, mesActual, diaActual).show()
        }
    }
    private fun esMayorDe13(fechaStr: String): Boolean {
        return try {
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            val fechaNacimiento = LocalDate.parse(fechaStr, formatter)
            val hoy = LocalDate.now()
            val edad = Period.between(fechaNacimiento, hoy).years
            edad >= 13
        } catch (e: DateTimeParseException) {
            false
        }
    }
    private fun obtenerInteresesSeleccionados(): List<String> {
        val intereses = mutableListOf<String>()
        if (binding.cbMusica.isChecked) intereses.add(getString(R.string.interes_musica))
        if (binding.cbDeporte.isChecked) intereses.add(getString(R.string.interes_deporte))
        if (binding.cbLectura.isChecked) intereses.add(getString(R.string.interes_lectura))
        if (binding.cbTecnologia.isChecked) intereses.add(getString(R.string.interes_tecnologia))
        if (binding.cbViajes.isChecked) intereses.add(getString(R.string.interes_viajes))
        return intereses
    }
    private fun validarFormulario(): Boolean {
        // Implementaremos todas las validaciones aquí.
        var esValido = true

        // Validar que el nombre no esté vacío
        if (binding.etNombres.text.isNullOrBlank()) {
            binding.tilNombre.error = getString(R.string.error_campo)
            esValido = false
        } else {
            binding.tilNombre.error = null // Limpiar error
        }
        // Validar que el nombre no esté vacío
        if (binding.etApellidos.text.isNullOrBlank()) {
            binding.tilApellidos.error = getString(R.string.error_campo)
            esValido = false
        } else {
            binding.tilApellidos.error = null // Limpiar error
        }
        // Validación de teléfono
        val telefono = binding.etTelefono.text.toString().trim()
        if (telefono.isEmpty()) {
            binding.tilTelefono.error = getString(R.string.error_campo)
            esValido = false
        } else if (telefono.length != 10) {
            binding.tilTelefono.error = getString(R.string.error_telefono_invalido)
            esValido = false
        } else {
            binding.tilTelefono.error = null
        }
        // Validar Fecha de Nacimiento
        val fechaTexto = binding.etFechaNacimiento.text.toString().trim()
        when {
            fechaTexto.isEmpty() -> {
                binding.tilFechaNacimiento.error = getString(R.string.error_fecha_obligatoria)
                esValido = false
            }
            !esMayorDe13(fechaTexto) -> {
                binding.tilFechaNacimiento.error = getString(R.string.error_edad_minima)
                esValido = false
            }
            else -> {
                binding.tilFechaNacimiento.error = null
            }
        }


        // Validar Género
        if (binding.etGenero.text.isNullOrBlank()) {
            binding.tilGenero.error = getString(R.string.error_genero)
            esValido = false
        } else {
            binding.tilGenero.error = null
        }
        // Validar Correo Electrónico
        val email = binding.etEmail.text.toString().trim()
        when {
            email.isEmpty() -> {
                binding.tilEmail.error = getString(R.string.error_email_obligatorio)
                esValido = false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding.tilEmail.error = getString(R.string.error_email_invalido)
                esValido = false
            }
            else -> {
                binding.tilEmail.error = null
            }
        }

        return esValido
    }

    private fun setupFotoPerfil() {
        binding.btnSeleccionarFoto.setOnClickListener {
                pickImageLauncher.launch("image/*")
            }
        }
}