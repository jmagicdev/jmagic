package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Magma Phoenix")
@Types({Type.CREATURE})
@SubTypes({SubType.PHOENIX})
@ManaCost("3RR")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.RARE), @Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class MagmaPhoenix extends Card
{
	public static final class FarewellGift extends EventTriggeredAbility
	{
		public FarewellGift(GameState state)
		{
			super(state, "When Magma Phoenix dies, it deals 3 damage to each creature and each player.");
			this.addPattern(whenThisDies());
			this.addEffect(permanentDealDamage(3, CREATURES_AND_PLAYERS, "Magma Phoenix deals 3 damage to each creature and each player."));
		}
	}

	public static final class ContractRenewal extends ActivatedAbility
	{
		public ContractRenewal(GameState state)
		{
			super(state, "(3)(R)(R): Return Magma Phoenix from your graveyard to your hand.");

			this.setManaCost(new ManaPool("3RR"));

			SetGenerator thisCard = ABILITY_SOURCE_OF_THIS;

			EventType.ParameterMap parameters = new EventType.ParameterMap();
			parameters.put(EventType.Parameter.CAUSE, This.instance());
			parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
			parameters.put(EventType.Parameter.OBJECT, thisCard);
			this.addEffect(new EventFactory(EventType.MOVE_OBJECTS, parameters, "Return Magma Phoenix from your graveyard to your hand."));

			this.activateOnlyFromGraveyard();
		}
	}

	public MagmaPhoenix(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new FarewellGift(state));
		this.addAbility(new ContractRenewal(state));
	}
}
