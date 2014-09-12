package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Xathrid Slyblade")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.ASSASSIN})
@ManaCost("2B")
@ColorIdentity({Color.BLACK})
public final class XathridSlyblade extends Card
{
	public static final class XathridSlybladeAbility1 extends ActivatedAbility
	{
		public XathridSlybladeAbility1(GameState state)
		{
			super(state, "(3)(B): Until end of turn, Xathrid Slyblade loses hexproof and gains first strike and deathtouch.");
			this.setManaCost(new ManaPool("(3)(B)"));

			ContinuousEffect.Part loseAbilities = new ContinuousEffect.Part(ContinuousEffectType.REMOVE_ABILITY_FROM_OBJECT);
			loseAbilities.parameters.put(ContinuousEffectType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
			loseAbilities.parameters.put(ContinuousEffectType.Parameter.ABILITY, Identity.instance(org.rnd.jmagic.abilities.keywords.Hexproof.class));
			this.addEffect(createFloatingEffect("Until end of turn, Xathrid Slyblade loses hexproof", loseAbilities));

			ContinuousEffect.Part gainAbilities = addAbilityToObject(ABILITY_SOURCE_OF_THIS,//
					org.rnd.jmagic.abilities.keywords.FirstStrike.class,//
					org.rnd.jmagic.abilities.keywords.Deathtouch.class);
			this.addEffect(createFloatingEffect("and gains first strike and deathtouch.", gainAbilities));
		}
	}

	public XathridSlyblade(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Hexproof (This creature can't be the target of spells or abilities
		// your opponents control.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Hexproof(state));

		// (3)(B): Until end of turn, Xathrid Slyblade loses hexproof and gains
		// first strike and deathtouch. (It deals combat damage before creatures
		// without first strike. Any amount of damage it deals to a creature is
		// enough to destroy it.)
		this.addAbility(new XathridSlybladeAbility1(state));
	}
}
