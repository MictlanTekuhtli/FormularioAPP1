# 📱 Formulario de Creación de Perfil – Android App

Aplicación Android nativa desarrollada en **Kotlin** con **Android Studio** que permite a los usuarios registrar un perfil personal mediante un formulario completo con validaciones robustas y una pantalla de resumen (reporte) final.

<p align="center">
  <img src="https://img.shields.io/badge/Platform-Android-brightgreen" alt="Platform">
  <img src="https://img.shields.io/badge/Min%20SDK-26%20(Oreo)-blue" alt="Min SDK">
  <img src="https://img.shields.io/badge/Language-Kotlin-purple" alt="Language">
  <img src="https://img.shields.io/badge/License-MIT-yellow" alt="License">
</p>

---

## 🎯 Objetivo del Proyecto

Cumplir con los requisitos de una actividad académica/práctica que solicita un formulario de creación de perfil con campos específicos, validaciones estrictas, soporte multi‑idioma y una segunda pantalla de reporte.

### ✅ Requerimientos del formulario

| Campo / Característica | Estado |
|------------------------|--------|
| **Nombre(s)** y **Apellidos** | ✔️ Obligatorios |
| **Fecha de nacimiento** | ✔️ Selección mediante `DatePickerDialog`. No permite escritura manual. |
| **Validación de edad mínima** | ✔️ Solo permite 13 años o más (cálculo con `java.time`). |
| **Género** | ✔️ Spinner con opciones fijas: Masculino, Femenino, No binario. |
| **Número de teléfono** | ✔️ Lada fija `+52` + 10 dígitos. Validación de longitud exacta. |
| **Correo electrónico** | ✔️ Formato válido usando `Patterns.EMAIL_ADDRESS`. |
| **Intereses personales** | ✔️ 5 opciones con `CheckBox` (Música, Deporte, Lectura, Tecnología, Viajes). Opcional. |
| **Validaciones y mensajes de error** | ✔️ Errores en tiempo real y al enviar el formulario. |
| **Botón "Crear perfil"** | ✔️ Solo habilita la navegación si todos los campos obligatorios son válidos. |
| **Pantalla de reporte** | ✔️ Muestra todos los datos ingresados de forma organizada. |
| **Internacionalización** | ✔️ Español e inglés mediante `strings.xml`. |
| **API mínima 26 (Android 8.0)** | ✔️  |

---

## ✨ Funcionalidades Extra (No requeridas originalmente)

Para mejorar la experiencia de usuario y la calidad del proyecto, se agregaron las siguientes características:

- **📷 Foto de perfil**  
  - Selección de imagen desde la galería.  
  - Manejo de permisos según versión de Android (`READ_MEDIA_IMAGES` / `READ_EXTERNAL_STORAGE`).  
  - Vista previa en un `CircleImageView`.  
  - La imagen se envía a la pantalla de reporte y se muestra allí.

- **🎨 Interfaz moderna con Material Design 3**  
  - Uso de `TextInputLayout`, `MaterialButton`, `MaterialCardView`, etc.  
  - Íconos de limpieza (`clear_text`) en campos de texto.  
  - Botón de regreso en la pantalla de reporte.

- **🧩 Arquitectura limpia y mantenible**  
  - Uso de **View Binding** para acceder a las vistas de forma segura.  
  - Clase `PerfilUsuario` como modelo de datos `Parcelable` (implementación manual para evitar conflictos de plugins).  
  - Separación de responsabilidades en funciones pequeñas.

- **📱 Ajuste de márgenes dinámicos**  
  - Implementación de `WindowInsets` para evitar solapamiento con la barra de estado (batería, hora).

---

## 🛠️ Tecnologías y Librerías Utilizadas

| Librería / Herramienta | Uso |
|------------------------|-----|
| **Kotlin** | Lenguaje principal. |
| **AndroidX** | Componentes modernos de Android. |
| **Material Components** | `TextInputLayout`, `MaterialButton`, `MaterialCardView`, etc. |
| **View Binding** | Vinculación segura de vistas. |
| **Glide** | Carga eficiente de imágenes (foto de perfil). |
| **CircleImageView** | Imagen redonda para la foto de perfil. |
| **Java Time API** | Cálculo de edad sin depender de `Calendar`. |
| **DatePickerDialog** | Selección nativa de fecha. |

---

## 🚀 Cómo Ejecutar el Proyecto

1. **Clonar el repositorio**  
   ```bash
   gh repo clone MictlanTekuhtli/FormularioAPP1
