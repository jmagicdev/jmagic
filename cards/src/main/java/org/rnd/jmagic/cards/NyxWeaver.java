package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Nyx Weaver")
@Types({Type.ENCHANTMENT, Type.CREATURE})
@SubTypes({SubType.SPIDER})
@ManaCost("1BG")
@ColorIdentity({Color.BLACK, Color.GREEN})
public final class NyxWeaver extends Card
{
	public static final class NyxWeaverAbility1 extends EventTriggeredAbility
	{
		public NyxWeaverAbility1(GameState state)
		{
			super(state, "At the beginning of your upkeep, put the top two cards of your library into your graveyard.");
			this.addPattern(atTheBeginningOfYourUpkeep());
			this.addEffect(millCards(You.instance(), 2, "Put the top two cards of your library into your graveyard."));
		}
	}

	public static final class NyxWeaverAbility2 extends ActivatedAbility
	{
		public NyxWeaverAbility2(GameState state)
		{
			super(state, "(1)(B)(G), Exile Nyx Weaver: Return target card from your graveyard to your hand.");
			this.setManaCost(new ManaPool("(1)(B)(G)"));
			this.addCost(exileThis("Nyx Weaver"));

			SetGenerator deadThings = InZone.instance(GraveyardOf.instance(You.instance()));
			SetGenerator target = targetedBy(this.addTarget(deadThings, "target card from your graveyard"));
			this.addEffect(putIntoHand(target, You.instance(), "Return target card from your graveyard to your hand."));
		}
	}

	public NyxWeaver(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// Reach
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Reach(state));

		// At the beginning of your upkeep, put the top two cards of your
		// library into your graveyard.
		this.addAbility(new NyxWeaverAbility1(state));

		// (1)(B)(G), Exile Nyx Weaver: Return target card from your graveyard
		// to your hand.
		this.addAbility(new NyxWeaverAbility2(state));
	}
}
