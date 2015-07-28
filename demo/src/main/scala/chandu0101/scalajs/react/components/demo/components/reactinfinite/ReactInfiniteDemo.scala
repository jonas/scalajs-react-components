package chandu0101.scalajs.react.components.demo.components.reactinfinite

import chandu0101.scalajs.react.components.demo.components.CodeExample
import chandu0101.scalajs.react.components.listviews.ReactInfinite
import chandu0101.scalajs.react.components.optionselectors.{ReactSelect, SelectOption}
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._

import scala.scalajs.js
import scala.scalajs.js.Dynamic.{literal => json}
import scala.scalajs.js.{Array => JArray}
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import scala.scalajs.js
import scala.scalajs.js
import scala.scalajs.js.undefined
import scala.scalajs.js.UndefOr
import scalacss.Defaults._
import scalacss.ScalaCssReact._

object ReactInfiniteDemo
{

  val code =
    """
      |
      |   val data = (1 to 500).toVector.map(i => s"List Item $i")
      |
      |   <.div(
      |          if(S.isLoading) <.div("Loading ..")
      |          else ReactInfinite(elementHeight = 40,
      |          containerHeight = 400)(S.data.map(B.renderRow _))
      |        )
      |
    """.stripMargin

  object styles extends StyleSheet.Inline {
    import dsl._
    val container = style(display.flex,
    justifyContent.center,
    alignItems.center,
    width(65 %%))

    val item = style(
      width(300 px),
      textAlign.center,
      height(70 px),
      padding(20 px)
    )
    val border = style(borderBottom :=! "2px solid rgba(0, 0, 0, 0.1)",
    marginLeft(4 px))
  }


  case class State(isLoading : Boolean = true,data : Vector[String] = Vector())

  class Backend(t: BackendScope[_, State]) {


    def renderRow(s : String) : ReactElement = {
      <.div(styles.item, s ,^.key := s,
      <.div(styles.border)
      )
    }

    def loadData() = {
      val data = (1 to 500).toVector.map(i => s"List Item $i")
      t.modState(_.copy(isLoading = false,data = data))
    }

  }


  val component = ReactComponentB[Unit]("ReactSelectDemo")
    .initialState(State())
    .backend(new Backend(_))
    .render((P, S, B) => {
    <.div(
      CodeExample(code, "Demo")(
        <.div( styles.container,
          if(S.isLoading) <.div("Loading ..")
          else ReactInfinite(elementHeight = 70,
          containerHeight = 400)(S.data.map(B.renderRow _))
        )
      )
    )
  })
    .componentDidMount(scope => scope.backend.loadData())
    .buildU



  def apply() = component()

}
