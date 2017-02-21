package demo.components
package materialui

import chandu0101.macros.tojs.GhPagesMacros
import chandu0101.scalajs.react.components.materialui._
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._

import scala.scalajs.js

object MuiPopoverDemo {
  val code = GhPagesMacros.exampleSource

  // EXAMPLE:START

  private case class OriginChoice[T](ts: Seq[T], label: String)(set: T => Callback, fromState: State => T, str: T => String) {
    val action: (ReactEvent, Int, js.Any) => Callback =
      (e, idx, any) => set(ts(idx))

    val items: ReactNode =
      ts.map(
        t => MuiMenuItem(value = str(t), primaryText = str(t))()
      ).toJsArray

    def menu(S: State): ReactElement =
      <.div(
        ^.key := label,
        <.label(
          label,
          ^.width := "400px"
        ),
        MuiDropDownMenu(
          onChange = action,
          value    = str(fromState(S))
        )(items)
      )
  }

  case class State(open: Boolean, target: Origin, anchor: Origin)

  private case class Backend($: BackendScope[Unit, State]) {

    val ref = Ref[TopNode]("theRef")

    val toggle: Callback =
      $.modState(s => s.copy(open = !s.open))

    val anchorH = OriginChoice(Horizontal.values, "Change anchor horizontal")(
      t => $.modState(s => s.copy(anchor = s.anchor.copy(horizontal = t))), _.anchor.horizontal, _.value
    )
    val anchorV = OriginChoice(Vertical.values, "Change anchor vertical")(
      t => $.modState(s => s.copy(anchor = s.anchor.copy(vertical = t))), _.anchor.vertical, _.value
    )
    val targetH = OriginChoice(Horizontal.values, "Change target horizontal")(
      t => $.modState(s => s.copy(target = s.target.copy(horizontal = t))), _.target.horizontal, _.value
    )
    val targetV = OriginChoice(Vertical.values, "Change target vertical")(
      t => $.modState(s => s.copy(target = s.target.copy(vertical = t))), _.target.vertical, _.value
    )

    val originChoices = Seq(anchorV, anchorH, targetV, targetH)

    def render(S: State) = {
      <.div(
        CodeExample(code, "MuiPopoverDemo")(
          <.div(
            <.div(
              ^.ref := ref,
              MuiRaisedButton(
                onTouchTap = (e: TouchTapEvent) => toggle,
                label = "Click on me to show a popover"
              )()
            ),

            originChoices.map(_.menu(S)),

            MuiPopover(
              open = S.open,
              anchorEl = ref($),
              anchorOrigin = S.anchor,
              targetOrigin = S.target,
              onRequestClose = (s: String) => toggle
            )(
              <.div(
                ^.padding := "20px",
                <.h2("here is an arbitrary popover"),
                <.p("Hi - here is some content"),
                MuiRaisedButton(
                  primary = true,
                  label = "here is a button",
                  onTouchTap = (e: TouchTapEvent) => toggle
                )()
              )
            )
          )
        )
      )
    }
  }

  private val component = ReactComponentB[Unit]("MuiPopoverDemo")
    .initialState(State(
      open = false,
      target = Origin(Vertical.top,    Horizontal.left),
      anchor = Origin(Vertical.bottom, Horizontal.left)
    ))
    .renderBackend[Backend]
    .build

  // EXAMPLE:END

  def apply(): ReactElement =
    component()
}
