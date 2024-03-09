package com.unicauca.mathsus30.ui.theme

import android.graphics.drawable.Icon
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import org.mariuszgromada.math.mxparser.Function


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Body() {
    var expression by rememberSaveable { mutableStateOf("") }
    var x0 by rememberSaveable { mutableStateOf("") }
    var x1 by rememberSaveable { mutableStateOf("") }
    var epsilon by rememberSaveable { mutableStateOf("") }
    Column {


        Text(
            text = "Método de la Secante",
            fontSize = 28.sp,
            color = Color.Black,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.SemiBold,
            fontFamily = FontFamily.Serif,
            lineHeight = 1.em
        )
        TextField(
            value = expression,
            onValueChange = { expression = it },
            label = { Text(text = "Expresión") }
        )
        TextField(
            value = x0,
            onValueChange = { x0 = it },
            label = { Text(text = "Limite inferio") }
        )

        TextField(
            value = x1,
            onValueChange = { x1 = it },
            label = { Text(text = "Límite superio") }
        )
        TextField(
            value = epsilon,
            onValueChange = { epsilon = it },
            label = { Text(text = "Tolerancia") }
        )


        Button(
            onClick = { x0 = expression }) {
            Icon(Icons.Rounded.CheckCircle, contentDescription = null)
            Text(text = "Calcular")

        }
        Text(text = "la expresion es: $x0")
        Box {
            Secante(x0 = 3.0, x1 = 5.0, f = expression, epsilon = 0.1)

        }


    }

}


@Composable
fun Secante(
    x0: Double,
    x1: Double,
    f: String,
    epsilon: Double
) {
    var xi_1 = x0.toDouble()
    var xi = x1.toDouble()
    var xiPlus1: Double? = null
    var root: Double? = null
    var contador = 0
    var error: Double
    var maxIterations = 100


    Box(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .horizontalScroll(rememberScrollState())
    ) {
        Column {


            /*
            Row {
                Text(text = " n ")
                Text(text = " xi_1     ")
                Text(text = " xi     ")
                Text(text = " fxi_1     ")
                Text(text = " fxi     ")
                Text(text = " xi+1     ")
                Text(text = " error     ")

            }
             */

            for (iteration in 1..maxIterations) {
                contador++

                val fxi_1 = Metodo(a = xi_1, f = f)
                val fxi = Metodo(a = xi, f = f)

                xiPlus1 = xi - (fxi * (xi_1 - xi)) / (fxi_1 - fxi)
                error = ((xiPlus1!! - xi) / xiPlus1!!) * 100

                /*
                Row {
                    Text(text = " $contador ")
                    Text(text = " $xi_1     ")
                    Text(text = " $xi     ")
                    Text(text = " $fxi_1     ")
                    Text(text = " $fxi     ")
                    Text(text = " $xiPlus1     ")
                    Text(text = " $error     ")

                }
                 */


                if (Math.abs(xiPlus1!! - xi) < epsilon.toDouble()) {
                    root = xiPlus1
                    break
                }
                xi_1 = xi
                xi = xiPlus1 as Double
            }
            Text(text = "$root")

        }
    }


}

@Composable
fun Metodo(a: Double, f: String): Double {
    var f = Function("f", f, "x")
    var fa = org.mariuszgromada.math.mxparser.Expression("f(${a})", f).calculate()
    return fa
}