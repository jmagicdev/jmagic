package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Calcite Snapper")
@Types({Type.CREATURE})
@SubTypes({SubType.TURTLE})
@ManaCost("1UU")
@Printings({@Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class CalciteSnapper extends Card
{
	public static final class Convert extends EventTriggeredAbility
	{
		public Convert(GameState state)
		{
			super(state, "Whenever a land enters the battlefield under your control, you may switch Calcite Snapper's power and toughness until end of turn.");
			this.addPattern(landfall());

			ContinuousEffect.Part switchPt = new ContinuousEffect.Part(ContinuousEffectType.SWITCH_POWER_AND_TOUGHNESS);
			switchPt.parameters.put(ContinuousEffectType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);

			EventFactory effect = createFloatingEffect("Switch Calcite Snapper's power and toughness until end of turn", switchPt);
			this.addEffect(youMay(effect, "You may switch Calcite Snapper's power and toughness until end of turn."));
		}
	}

	public CalciteSnapper(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(4);

		// Shroud
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Shroud(state));

		// Whenever a land enters the battlefield under your control, you may
		// switch Calcite Snapper's power and toughness until end of turn.
		this.addAbility(new Convert(state));
	}
}
