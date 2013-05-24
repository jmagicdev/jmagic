package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Battlegrace Angel")
@Types({Type.CREATURE})
@SubTypes({SubType.ANGEL})
@ManaCost("3WW")
@Printings({@Printings.Printed(ex = Expansion.SHARDS_OF_ALARA, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class BattlegraceAngel extends Card
{
	public static final class ExaltedLifelink extends org.rnd.jmagic.abilityTemplates.ExaltedBase
	{
		public ExaltedLifelink(GameState state)
		{
			super(state, "Whenever a creature you control attacks alone, it gains lifelink until end of turn.");

			this.addEffect(addAbilityUntilEndOfTurn(this.thatCreature, org.rnd.jmagic.abilities.keywords.Lifelink.class, "It gains lifelink until end of turn."));
		}
	}

	public BattlegraceAngel(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Exalted(state));

		this.addAbility(new ExaltedLifelink(state));
	}
}
