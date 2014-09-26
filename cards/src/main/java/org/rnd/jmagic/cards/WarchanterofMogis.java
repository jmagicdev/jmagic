package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Warchanter of Mogis")
@Types({Type.CREATURE})
@SubTypes({SubType.MINOTAUR, SubType.SHAMAN})
@ManaCost("3BB")
@ColorIdentity({Color.BLACK})
public final class WarchanterofMogis extends Card
{
	public static final class WarchanterofMogisAbility0 extends EventTriggeredAbility
	{
		public WarchanterofMogisAbility0(GameState state)
		{
			super(state, "Inspired \u2014 Whenever Warchanter of Mogis becomes untapped, target creature you control gains intimidate until end of turn.");
			this.addPattern(inspired());

			SetGenerator yourCreatures = Intersect.instance(ControlledBy.instance(You.instance()), HasType.instance(Type.CREATURE));
			SetGenerator target = targetedBy(this.addTarget(yourCreatures, "target creature you control"));
			this.addEffect(addAbilityUntilEndOfTurn(target, org.rnd.jmagic.abilities.keywords.Intimidate.class, "Target creature you control gains intimidate until end of turn."));
		}
	}

	public WarchanterofMogis(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Inspired \u2014 Whenever Warchanter of Mogis becomes untapped, target
		// creature you control gains intimidate until end of turn. (A creature
		// with intimidate can't be blocked except by artifact creatures and/or
		// creatures that share a color with it.)
		this.addAbility(new WarchanterofMogisAbility0(state));
	}
}
