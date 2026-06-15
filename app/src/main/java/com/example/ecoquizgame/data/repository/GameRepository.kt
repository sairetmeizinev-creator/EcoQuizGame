package com.example.ecoquizgame.data.repository

import com.example.ecoquizgame.data.model.Question

class GameRepository {
    fun getQuestions(locale: String = "en"): List<Question> {
        return if (locale == "bg") {
            listOf(
                Question(1, "Кой газ е основно отговорен за глобалното затопляне?", listOf("Кислород", "Азот", "Въглероден диоксид", "Хелий"), listOf(2)),
                Question(2, "Кой енергиен източник е възобновяем?", listOf("Въглища", "Слънчева енергия", "Нефт", "Природен газ"), listOf(1)),
                Question(3, "Какво помага да се намали рециклирането?", listOf("Валежите", "Отпадъците", "Слънчевата светлина", "Вятъра"), listOf(1)),
                Question(4, "Кой предмет обикновено може да бъде компостиран?", listOf("Пластмасова бутилка", "Бананова кора", "Стъклен буркан", "Алуминиева кутия"), listOf(1)),
                Question(5, "Кой транспортен вариант има най-нисък въглероден отпечатък?", listOf("Вървене пеша", "Кола", "Мотоциклет", "Самолет"), listOf(0)),
                Question(6, "Какво е биоразнообразие?", listOf("Брой пътища", "Разнообразие от форми на живот", "Количество замърсяване", "Видове облаци"), listOf(1)),
                Question(7, "Коя дейност пести вода?", listOf("Дълги душове", "Поправка на течове", "Оставени отворени кранове", "Миене на коли всеки ден"), listOf(1)),
                Question(8, "Какво увеличава обезлесяването?", listOf("Плодородието на почвата", "Абсорбцията на въглерод", "CO2 в атмосферата", "Размера на дъждовните гори"), listOf(2)),
                Question(9, "Коя е често срещана причина за замърсяването на океаните?", listOf("Хартиени книги", "Пластмасови отпадъци", "Вятърна енергия", "Велосипеди"), listOf(1)),
                Question(10, "Кой е устойчив навик?", listOf("Пластмаса за еднократна употреба", "Бърза мода", "Използване на торби за многократна употреба", "Изгаряне на боклук"), listOf(2)),
                Question(11, "Каква е основната цел на национален парк?", listOf("Строеж на молове", "Защита на екосистеми", "Добив на ресурси", "Разширяване на пътища"), listOf(1)),
                Question(12, "Какво може да намали консумацията на електроенергия у дома?", listOf("LED крушки", "Оставяне на лампите светнати", "Стари уреди", "Отворена врата на хладилника"), listOf(0)),
                Question(13, "Кой материал се рециклира най-масово?", listOf("Стъкло", "Хартия", "Пластмаса", "Храна"), listOf(1)),
                Question(14, "Замърсяването на въздуха може да причини:", listOf("Здравни проблеми", "По-чисто небе", "Повече гори", "По-ниски температури"), listOf(0)),
                Question(15, "Какво означава „намаляване“ (reduce) в 3R?", listOf("Купувай повече", "Използвай по-малко", "Изхвърляй по-бързо", "Гори отпадъци"), listOf(1)),
                Question(16, "Основен източник на метан е:", listOf("Животновъдство", "Слънчеви панели", "Вятърни турбини", "Хидроенергия"), listOf(0)),
                Question(17, "Кое действие подкрепя устойчивостта?", listOf("Свръхпотребление", "Ремонтиране на предмети", "Игнориране на отпадъците", "Шофиране на къси разстояния"), listOf(1)),
                Question(18, "Какво са е-отпадъците?", listOf("Електронни боклуци", "Органични отпадъци", "Хартиени отпадъци", "Градински отпадъци"), listOf(0)),
                Question(19, "Защо се садят дървета в градовете?", listOf("Увеличаване на жегата", "Влошаване качеството на въздуха", "Подобряване на въздуха и сянка", "Използване на повече вода"), listOf(2)),
                Question(20, "Какво представляват климатичните промени?", listOf("Кратко метеорологично събитие", "Дългосрочна промяна в климата", "Дневна температура", "Сезонен дъжд"), listOf(1))
            )
        } else {
            listOf(
                Question(1, "What gas is primarily responsible for global warming?", listOf("Oxygen", "Nitrogen", "Carbon Dioxide", "Helium"), listOf(2)),
                Question(2, "Which energy source is renewable?", listOf("Coal", "Solar", "Oil", "Natural Gas"), listOf(1)),
                Question(3, "What does recycling help reduce?", listOf("Rainfall", "Waste", "Sunlight", "Wind"), listOf(1)),
                Question(4, "Which item can usually be composted?", listOf("Plastic bottle", "Banana peel", "Glass jar", "Aluminum can"), listOf(1)),
                Question(5, "Which transport option has the lowest carbon footprint?", listOf("Walking", "Car", "Motorbike", "Plane"), listOf(0)),
                Question(6, "What is biodiversity?", listOf("Number of roads", "Variety of life forms", "Amount of pollution", "Types of clouds"), listOf(1)),
                Question(7, "Which activity saves water?", listOf("Long showers", "Fixing leaks", "Running taps", "Washing cars daily"), listOf(1)),
                Question(8, "What does deforestation increase?", listOf("Soil fertility", "Carbon absorption", "CO2 in atmosphere", "Rainforest size"), listOf(2)),
                Question(9, "What is a common cause of ocean pollution?", listOf("Paper books", "Plastic waste", "Wind power", "Bicycles"), listOf(1)),
                Question(10, "Which is a sustainable habit?", listOf("Single-use plastics", "Fast fashion", "Using reusable bags", "Burning trash"), listOf(2)),
                Question(11, "What is the main purpose of a national park?", listOf("Build malls", "Protect ecosystems", "Mine resources", "Expand roads"), listOf(1)),
                Question(12, "What can reduce electricity use at home?", listOf("LED bulbs", "Leaving lights on", "Old appliances", "Open fridge door"), listOf(0)),
                Question(13, "Which material is most widely recycled?", listOf("Glass", "Paper", "Plastic", "Food"), listOf(1)),
                Question(14, "Air pollution can cause:", listOf("Health problems", "Cleaner skies", "More forests", "Lower temperatures"), listOf(0)),
                Question(15, "What does 'reduce' in 3R mean?", listOf("Buy more", "Use less", "Throw away faster", "Burn waste"), listOf(1)),
                Question(16, "A major source of methane is:", listOf("Cattle farming", "Solar panels", "Wind turbines", "Hydropower"), listOf(0)),
                Question(17, "Which action supports sustainability?", listOf("Overconsumption", "Repairing items", "Ignoring waste", "Driving short distances"), listOf(1)),
                Question(18, "What is e-waste?", listOf("Electronic trash", "Organic waste", "Paper waste", "Garden waste"), listOf(0)),
                Question(19, "Why plant trees in cities?", listOf("Increase heat", "Reduce air quality", "Improve air and shade", "Use more water"), listOf(2)),
                Question(20, "What is climate change?", listOf("Short weather event", "Long-term climate shift", "Daily temperature", "Seasonal rain"), listOf(1))
            )
        }
    }
}
