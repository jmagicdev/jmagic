package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Zurgo Helmsmasher")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.ORC, SubType.WARRIOR})
@ManaCost("2RWB")
@ColorIdentity({Color.RED, Color.WHITE, Color.BLACK})
public final class ZurgoHelmsmasher extends Card
{
	public static final class ZurgoHelmsmasherAbility2 extends StaticAbility
	{
		public ZurgoHelmsmasherAbility2(GameState state)
		{
			super(state, "Zurgo Helmsmasher has indestructible as long as it's your turn.");
			this.addEffectPart(addAbilityToObject(This.instance(), org.rnd.jmagic.abilities.keywords.Indestructible.class));
			this.canApply = Both.instance(this.canApply, Intersect.instance(TurnOf.instance(You.instance()), CurrentTurn.instance()));
		}
	}

	public ZurgoHelmsmasher(GameState state)
	{
		super(state);

		this.setPower(7);
		this.setToughness(2);

		// Haste
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));

		// Zurgo Helmsmasher attacks each combat if able.
		this.addAbility(new org.rnd.jmagic.abilities.AttacksEachTurnIfAble(state, this.getName()));

		// Zurgo Helmsmasher has indestructible as long as it's your turn.
		this.addAbility(new ZurgoHelmsmasherAbility2(state));

		// Whenever a creature dealt damage by Zurgo Helmsmasher this turn dies,
		// put a +1/+1 counter on Zurgo Helmsmasher.
		this.addAbility(new org.rnd.jmagic.abilities.VampireKill(state, "Zurgo Helmsmasher"));
	}
}
