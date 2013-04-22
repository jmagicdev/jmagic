package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Arctic Aven")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.BIRD})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE, Color.BLUE})
public final class ArcticAven extends Card
{
	public static final class ArcticAvenAbility1 extends StaticAbility
	{
		public ArcticAvenAbility1(GameState state)
		{
			super(state, "Arctic Aven gets +1/+1 as long as you control a Plains.");

			this.addEffectPart(modifyPowerAndToughness(This.instance(), 1, 1));

			SetGenerator youControl = ControlledBy.instance(You.instance());
			SetGenerator plains = HasSubType.instance(SubType.PLAINS);
			this.canApply = Both.instance(this.canApply, Intersect.instance(youControl, plains));
		}
	}

	public static final class ArcticAvenAbility2 extends ActivatedAbility
	{
		public ArcticAvenAbility2(GameState state)
		{
			super(state, "(W): Arctic Aven gains lifelink until end of turn.");
			this.setManaCost(new ManaPool("(W)"));
			this.addEffect(addAbilityUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, org.rnd.jmagic.abilities.keywords.Lifelink.class, "Arctic Aven gains lifelink until end of turn."));
		}
	}

	public ArcticAven(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Arctic Aven gets +1/+1 as long as you control a Plains.
		this.addAbility(new ArcticAvenAbility1(state));

		// (W): Arctic Aven gains lifelink until end of turn. (Damage dealt by
		// this creature also causes you to gain that much life.)
		this.addAbility(new ArcticAvenAbility2(state));
	}
}
