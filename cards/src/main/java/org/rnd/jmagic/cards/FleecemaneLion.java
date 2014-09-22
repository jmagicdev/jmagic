package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import static org.rnd.jmagic.Convenience.*;

@Name("Fleecemane Lion")
@Types({Type.CREATURE})
@SubTypes({SubType.CAT})
@ManaCost("GW")
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class FleecemaneLion extends Card
{
	public static class MonstrousAbilities extends StaticAbility
	{
		public MonstrousAbilities(GameState state)
		{
			super(state, "As long as Fleecemane Lion is monstrous, it has hexproof and indestructible.");

			this.canApply = ThisIsMonstrous.instance();

			this.addEffectPart(addAbilityToObject(This.instance(), org.rnd.jmagic.abilities.keywords.Hexproof.class, org.rnd.jmagic.abilities.keywords.Indestructible.class));
		}
	}

	public FleecemaneLion(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.Monstrosity(state, "(3)(G)(W)", 1));
		this.addAbility(new MonstrousAbilities(state));

		this.setPower(3);
		this.setToughness(3);
	}
}
