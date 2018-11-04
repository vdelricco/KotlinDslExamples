package com.delricco.kotlindslexample

import org.junit.Assert.assertEquals
import org.junit.Test

class BusinessDslTest {
    @Test
    fun assertExpectedBusinessData() {
        val business = business {
            name { "Generic.io" }

            address {
                street { "Agile St" }
                city { "Disruptville" }
            }

            employees {
                employee {
                    name { "Grace Elliot" }
                    id { "12345" }
                    title { "VP Engineering" }
                    salary { 125_000 }
                }

                employee {
                    name { "Jeff Jefferson" }
                    id { "54321" }
                    title { "Chief Jeff Officer" }
                    salary { 100_000 }
                }
            }
        }

        assertEquals("Generic.io", business.name)

        assertEquals("Agile St", business.address.street)
        assertEquals("Disruptville", business.address.city)

        assert(business.employees.size == 2)

        val employee1 = business.employees[0]
        val employee2 = business.employees[1]

        assertEquals("Grace Elliot", employee1.name)
        assertEquals("Jeff Jefferson", employee2.name)

        assertEquals("12345", employee1.id)
        assertEquals("54321", employee2.id)

        assertEquals("VP Engineering", employee1.title)
        assertEquals("Chief Jeff Officer", employee2.title)

        assertEquals(125_000, employee1.salary)
        assertEquals(100_000, employee2.salary)
    }
}
