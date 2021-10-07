package com.panyuro.spock.demo

import com.panyuro.spock.demo.exception.TooFewSidesException
import com.panyuro.spock.demo.model.Palette
import com.panyuro.spock.demo.model.Polygon
import com.panyuro.spock.demo.model.Renderer
import com.panyuro.spock.demo.model.ShapeFactory
import spock.lang.Specification
import spock.lang.Subject

import java.awt.Color


class ExampleSpecification extends Specification{
    //set up state once at the start of the specification, this is for things that should not change between individual test methods
    //在规范的开始设置状态一次，这是为了在各个测试方法之间不应该改变的东西
    void setupSpec(){
    }

    //will be run before every individual test method in this class.(将在该类中的每个单独测试方法之前运行。)
    // This can be used to set up a clean state at the start of each test.(这可用于在每次测试开始时设置干净状态。)
    void setup() {
    }

    //To clean up data or state at the end of every test method(在每个测试方法结束时清理数据或状态)
    void cleanup() {
    }

    //  for final teardown code, this method will be called once at the very end of running all the tests.
    // 对于最终的分解代码，该方法将在运行所有测试的最后调用一次。
    void cleanupSpec(){
    }
//a simple assertion
    def "should be a simple assertion"() {
        expect:
        1 == 1
    }

    //given when then
    def "should demo given-when-then"(){
        given:
        def user = new Polygon(10)

        when:
        int age = user.numberOfSides

        then:
        age == 10
    }

    //expecting exceptions
    def "should expect exceptions"(){
        when:
        new Polygon(0)

        then:
        thrown(TooFewSidesException)
    }

// data pipes
    def "should expect an exception to be throw for a number of invalid inputs"(){
        when:
        new Polygon(sides)

        then:
        thrown(TooFewSidesException)

        //The where block says "run this test with each of the following values: a negative value, zero, one and two".
        // where设置所有期望值
        where:
        sides << [-1,0,1,2]
    }

    def "should be able to create polygon with valid inputs"(){
        expect:
        new Polygon(sides).numberOfSides == sides

        //The where block says "run this test with each of the following values: a negative value, zero, one and two".
        where:
        sides << [3,4,5,6]
    }

    //data tables
    def "should use data tables for calculating max"(){
        // 如果我们只有一条语句设置了测试和断言，我们可以直接使用`expect`标签
        expect:
        Math.max(a,b) == max

        where:
        a | b | max
        1 | 2 | 2
        3 | 4 | 4
        5 | 9 | 9
    }

    //mocks 模拟出类或API来声明预期的行为
    def "should be able to mock a specific class"() {
        given:
        Renderer renderer = Mock()

        //@Subject只是为了标记我们正在测试的对象，对代码没有任何影响
        @Subject
        def ploygon = new Polygon(4, renderer)

        when:
        ploygon.draw()

        then:
        4 * renderer.drawLine()
    }
    
    //stubs 在测试的代码里提供数据或者值
    def "should be able to create a stubs"() {
        given:
        Palette palette = Stub()
        palette.getPrimaryColor() >> Color.red

        def renderer =  new Renderer(palette)

        expect:
        renderer.getForeGroundColor() == Color.red
    }

    // help methods
    def "should use a helper methods"() {
        given:
        Renderer renderer = Mock()
        def shapeFactory = new ShapeFactory(renderer)

        when:
        def polygon = shapeFactory.createDefaultPolygon()

        then:
        checkDefaultShape(polygon, renderer)
    }

    def void checkDefaultShape(Polygon polygon, Renderer renderer) {
        assert polygon.numberOfSides == 4
        assert renderer == renderer
    }

    //with 测试但个对象的多个属性
    def "should use #with()"() {
        given:
        Renderer renderer = Mock()
        def shapeFactory = new ShapeFactory(renderer)

        when:
        def polygon = shapeFactory.createDefaultPolygon()

        then:
        with(polygon){
            numberOfSides == 4
            renderer == render
        }
    }

    // verifyAll() 确保所有断言都在执行，而不管其中之一是否失败了,
    // 当使用with时，第一行测试失败了，第二行测试就不会执行了，而verifyAll 会运行所有行测试
    def "should use #verifyAll()"() {
        given:
        Renderer renderer = Mock()
        def shapeFactory = new ShapeFactory(renderer)

        when:
        def polygon = shapeFactory.createDefaultPolygon()

        then:
        verifyAll(polygon){
            numberOfSides == 5
            renderer == null
        }
    }
}
