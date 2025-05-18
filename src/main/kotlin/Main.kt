fun main() {
    val recipe = cookPasta("томатный") { ingredient ->
        // (1) — Функция как аргумент: stepFunc
        listOf(
            "1. Вскипятите воду",
            "2. Добавьте соль",
            "3. Положите $ingredient",
            "4. Варите 10 минут",
            "5. Слейте воду",
            "6. Добавьте немного масла"
        )
    }
    val withTopping = recipe("спагетти") // (5) — Каррирование: частичное применение
    val finalDish = withTopping("пармезан")
    println(finalDish)
}

fun cookPasta(
    sauce: String,
    stepFunc: (String) -> List<String> // (1) — функция как аргумент
): (String) -> (String) -> String {    // (2,5) — функции как результат + каррирование
    return fun(pastaType: String): (String) -> String {
        val steps = stepFunc(pastaType)
        val formattedSteps = steps.joinToString("\n") { "  $it" } // (3) — Встроенная функция высшего порядка
        return fun(topping: String): String {
            // (4) — Композиция: объединение шагов + соус + топпинг
            return buildString { // (3) — ещё одна встроенная функция
                appendLine("  === Рецепт приготовления пасты ===")
                appendLine(formattedSteps)
                appendLine("  7. Добавьте соус: $sauce")
                appendLine("  8. Украсьте топпингом: $topping")
                appendLine("  9. Подавайте на стол!")
            }
        }
    }
}