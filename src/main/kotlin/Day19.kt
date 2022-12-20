import java.awt.Robot

class Day19(val input: List<String>) {

    data class RobotRecipe(val name: String, val ingredients: Map<String, Int>) {

        fun hasIngredients(storage: Map<String, Int>): Boolean {
            return ingredients.all { (storage[it.key] ?: 0) >= it.value }
        }

        fun build(storage: Map<String, Int>): Map<String, Int> {
            return storage.map { it.key to it.value - (ingredients[it.key] ?: 0) }.toMap()
        }
        companion object {
            fun from(input: String): RobotRecipe {
                val robotParts = input.splitIgnoreEmpty(" ")
                val ingredients = robotParts.drop(4).let {
                    val num = (it.size) / 2
                    (0 until num).map { idx -> it[1+(idx*3)] to it[(idx*3)].toInt() }
                }.toMap()
                return RobotRecipe(robotParts[1], ingredients)
            }
        }
    }



    data class GameState(val recipes: List<RobotRecipe>, val storage: Map<String, Int>, val robots: Map<String, Int>) {

        fun score(): Int {
            return (storage["geode"] ?: 0) * 10000 + (robots["geode"] ?: 0) * 1000 + (robots["obsidian"]?:0) * 100 + (robots["ore"]?:0) * 10 + (robots["clay"]?:0) * 1

            //return (storage["geode"] ?: 0) * 10000 + (robots["geode"] ?: 0) * 1000 + (robots["obsidian"]?:0) * 100 + (robots["ore"]?:0) * 1 + (robots["clay"]?:0) * 10
        }
        private fun allBuildOptions(recipes: List<RobotRecipe>, storage: Map<String, Int>, alreadyCreated: List<RobotRecipe>): Set<List<RobotRecipe>> {
            val created = mutableSetOf(alreadyCreated)
            recipes.forEach { recipe ->
                if(recipe.hasIngredients(storage)) {
                    // consume and iterate
                    created.add(alreadyCreated + recipe)
                }
            }
            return created
        }
        fun runRound(): List<GameState> {
            // build
            val buildOptions = allBuildOptions(recipes, storage, listOf())
            return buildOptions.map { option ->
                var newStorage = storage.toMutableMap()
                var newRobots = robots.toMutableMap()
                //println(" Option: $option")
                for(recipe in option) {
                    newStorage = recipe.build(newStorage).toMutableMap()
                    newRobots[recipe.name] = (newRobots[recipe.name]?:0) + 1
                }
                for(harvestBot in robots) {
                    if(harvestBot.value>0) {
                        //println(" Collect: ${harvestBot.value} ${harvestBot.key}")
                    }
                    newStorage[harvestBot.key] = (newStorage[harvestBot.key]?:0) + harvestBot.value
                }
                GameState(recipes, newStorage, newRobots)
                // for each option create a new state with its new inventory
            }
        }

        override fun toString(): String {
            return "Blueprint: ${recipes.hashCode()}\n  Storage: $storage\n  Robots: $robots"
        }
    }


    fun solvePart1(): Int {
        val blueprints = input.map { line ->
            val parts = line.splitIgnoreEmpty(":", ".")
            val recipes = parts.drop(1).map { robot ->
                RobotRecipe.from(robot)
            }
            GameState(recipes,
                mapOf("ore" to 0, "clay" to 0, "obsidian" to 0, "geode" to 0),
                mapOf("ore" to 1, "clay" to 0, "obsidian" to 0, "geode" to 0))
        }
        val scores = blueprints.map {blueprint ->
            var states = listOf(blueprint)
            (1 .. 24).map { round ->
                //println("== Minute ${round}")
                states = states.flatMap { state ->
                    val newstates = state.runRound()
                    //println(newstates)
                    newstates
                }.sortedByDescending { it.score()  }.take(30000)
                //println(states.sortedByDescending { it.storage["geode"]!! }.first())
            }
            //println(states.sortedByDescending { it.storage["geode"]!! }.first())
            states.map{ it.storage["geode"]!! }.sortedDescending().first()
        }
        //println(scores)
        return scores.mapIndexed{ idx, score -> (idx+1) * score}.sum()
    }
    fun solvePart2(): Long {
        val blueprints = input.map { line ->
            val parts = line.splitIgnoreEmpty(":", ".")
            val recipes = parts.drop(1).map { robot ->
                RobotRecipe.from(robot)
            }
            GameState(recipes,
                mapOf("ore" to 0, "clay" to 0, "obsidian" to 0, "geode" to 0),
                mapOf("ore" to 1, "clay" to 0, "obsidian" to 0, "geode" to 0))
        }
        val scores = blueprints.take(3).map {blueprint ->
            var states = listOf(blueprint)
            (1 .. 32).map { round ->
                //println("== Minute ${round}")
                states = states.flatMap { state ->
                    val newstates = state.runRound()
                    //println(newstates)
                    newstates
                }.sortedByDescending { it.score()  }.take(30000)
                //println(states.sortedByDescending { it.storage["geode"]!! }.first())
            }
            //println(states.sortedByDescending { it.storage["geode"]!! }.first())
            states.map{ it.storage["geode"]!! }.sortedDescending().first()
        }
        //println(scores)
        return scores.map{score -> score}.multiply()
    }
}