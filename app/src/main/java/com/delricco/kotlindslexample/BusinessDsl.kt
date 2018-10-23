package com.delricco.kotlindslexample

@DslMarker annotation class BusinessDsl
data class Business(val name: String, val address: Address, val employees: List<Employee>)
data class Address(val street: String, val city: String)
data class Employee(val name: String, val id: String, val title: String, val salary: Int)

fun business(block: BusinessBuilder.() -> Unit) = BusinessBuilder().apply(block).build()

@BusinessDsl class BusinessBuilder {
    private var name = ""
    private var address = Address("", "")
    private val employees = mutableListOf<Employee>()

    fun name(block: () -> String) { name = block() }
    fun address(block: AddressBuilder.() -> Unit) { address = AddressBuilder().apply(block).build() }
    fun employees(block: EmployeeListBuilder.() -> Unit) { employees.addAll(EmployeeListBuilder().apply(block).build()) }
    fun build() = Business(name, address, employees)
}

@BusinessDsl class AddressBuilder {
    private var street = ""
    private var city = ""

    fun street(street: () -> String) { this.street = street() }
    fun city(city: () -> String) { this.city = city() }
    fun build() = Address(street, city)
}

@BusinessDsl class EmployeeListBuilder {
    private val employeeList = mutableListOf<Employee>()

    fun employee(block: EmployeeBuilder.() -> Unit) = employeeList.add(EmployeeBuilder().apply(block).build())
    fun build() = employeeList
}

@BusinessDsl class EmployeeBuilder {
    private var name: String = ""
    private var id: String = ""
    private var title: String = ""
    private var salary: Int = 0

    fun name(name: () -> String) = apply { this.name = name() }
    fun id(id: () -> String) = apply { this.id = id() }
    fun title(title: () -> String) = apply { this.title = title() }
    fun salary(salary: () -> Int) = apply { this.salary = salary() }
    fun build() = Employee(name, id, title, salary)
}
