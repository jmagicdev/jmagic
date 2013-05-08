package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Tajuru Preserver")
@Types({Type.CREATURE})
@SubTypes({SubType.SHAMAN, SubType.ELF})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class TajuruPreserver extends Card
{
	public static final class Preserve extends StaticAbility
	{
		public Preserve(GameState state)
		{
			super(state, "Spells and abilities your opponents control can't cause you to sacrifice permanents.");

			SimpleEventPattern sacrifice = new SimpleEventPattern(EventType.SACRIFICE_ONE_PERMANENT);
			sacrifice.put(EventType.Parameter.CAUSE, ControlledBy.instance(OpponentsOf.instance(You.instance()), Stack.instance()));
			sacrifice.put(EventType.Parameter.OBJECT, ControlledBy.instance(You.instance()));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(sacrifice));
			this.addEffectPart(part);
		}
	}

	public TajuruPreserver(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Spells and abilities your opponents control can't cause you to
		// sacrifice permanents.
		this.addAbility(new Preserve(state));
	}
}
