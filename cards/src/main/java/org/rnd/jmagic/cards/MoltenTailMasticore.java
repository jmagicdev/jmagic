package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Molten-Tail Masticore")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.MASTICORE})
@ManaCost("4")
@ColorIdentity({})
public final class MoltenTailMasticore extends Card
{
	public static final class MoltenTailMasticoreAbility0 extends EventTriggeredAbility
	{
		public MoltenTailMasticoreAbility0(GameState state)
		{
			super(state, "At the beginning of your upkeep, sacrifice Molten-Tail Masticore unless you discard a card.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			EventFactory factory = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "Sacrifice Molten-Tail Masticore unless you discard a card.");
			factory.parameters.put(EventType.Parameter.IF, Identity.instance(youMay(discardCards(You.instance(), 1, "Discard a card."), "You may discard a card.")));
			factory.parameters.put(EventType.Parameter.ELSE, Identity.instance(sacrificeThis("Molten-Tail Masticore")));
			this.addEffect(factory);
		}
	}

	public static final class MoltenTailMasticoreAbility1 extends ActivatedAbility
	{
		public MoltenTailMasticoreAbility1(GameState state)
		{
			super(state, "(4), Exile a creature card from your graveyard: Molten-Tail Masticore deals 4 damage to target creature or player.");
			this.setManaCost(new ManaPool("(4)"));
			this.addCost(exile(You.instance(), Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(You.instance()))), 1, "Exile a creature card from your graveyard"));
			SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));
			this.addEffect(permanentDealDamage(4, target, "Molten-Tail Masticore deals 4 damage to target creature or player."));
		}
	}

	public static final class MoltenTailMasticoreAbility2 extends ActivatedAbility
	{
		public MoltenTailMasticoreAbility2(GameState state)
		{
			super(state, "(2): Regenerate Molten-Tail Masticore.");
			this.setManaCost(new ManaPool("(2)"));
			this.addEffect(regenerate(ABILITY_SOURCE_OF_THIS, "Regenerate Molten-Tail Masticore."));
		}
	}

	public MoltenTailMasticore(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// At the beginning of your upkeep, sacrifice Molten-Tail Masticore
		// unless you discard a card.
		this.addAbility(new MoltenTailMasticoreAbility0(state));

		// (4), Exile a creature card from your graveyard: Molten-Tail Masticore
		// deals 4 damage to target creature or player.
		this.addAbility(new MoltenTailMasticoreAbility1(state));

		// (2): Regenerate Molten-Tail Masticore.
		this.addAbility(new MoltenTailMasticoreAbility2(state));
	}
}
