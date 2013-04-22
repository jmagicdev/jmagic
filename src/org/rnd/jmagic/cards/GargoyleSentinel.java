package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Gargoyle Sentinel")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.GARGOYLE})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class GargoyleSentinel extends Card
{
	public static final class GargoyleSentinelAbility1 extends ActivatedAbility
	{
		public GargoyleSentinelAbility1(GameState state)
		{
			super(state, "(3): Until end of turn, Gargoyle Sentinel loses defender and gains flying.");
			this.setManaCost(new ManaPool("(3)"));

			ContinuousEffect.Part loseDefender = new ContinuousEffect.Part(ContinuousEffectType.REMOVE_ABILITY_FROM_OBJECT);
			loseDefender.parameters.put(ContinuousEffectType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
			loseDefender.parameters.put(ContinuousEffectType.Parameter.ABILITY, Identity.instance(org.rnd.jmagic.abilities.keywords.Defender.class));
			this.addEffect(createFloatingEffect("Gargoyle Sentinel loses defender", loseDefender));

			this.addEffect(addAbilityUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, org.rnd.jmagic.abilities.keywords.Flying.class, " and gains flying."));
		}
	}

	public GargoyleSentinel(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Defender (This creature can't attack.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));

		// (3): Until end of turn, Gargoyle Sentinel loses defender and gains
		// flying.
		this.addAbility(new GargoyleSentinelAbility1(state));
	}
}
