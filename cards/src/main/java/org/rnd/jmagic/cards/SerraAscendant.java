package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Serra Ascendant")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.MONK})
@ManaCost("W")
@Printings({@Printings.Printed(ex = Magic2011.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class SerraAscendant extends Card
{
	public static final class SerraAscendantAbility1 extends StaticAbility
	{
		public SerraAscendantAbility1(GameState state)
		{
			super(state, "As long as you have 30 or more life, Serra Ascendant gets +5/+5 and has flying.");

			this.addEffectPart(modifyPowerAndToughness(This.instance(), +5, +5));
			this.addEffectPart(addAbilityToObject(This.instance(), org.rnd.jmagic.abilities.keywords.Flying.class));

			this.canApply = Both.instance(this.canApply, Intersect.instance(Between.instance(30, null), LifeTotalOf.instance(You.instance())));
		}
	}

	public SerraAscendant(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Lifelink (Damage dealt by this creature also causes you to gain that
		// much life.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Lifelink(state));

		// As long as you have 30 or more life, Serra Ascendant gets +5/+5 and
		// has flying.
		this.addAbility(new SerraAscendantAbility1(state));
	}
}
