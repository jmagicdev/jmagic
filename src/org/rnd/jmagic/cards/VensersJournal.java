package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Venser's Journal")
@Types({Type.ARTIFACT})
@ManaCost("5")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.RARE)})
@ColorIdentity({})
public final class VensersJournal extends Card
{
	public static final class SpellbookAbility extends StaticAbility
	{
		public SpellbookAbility(GameState state)
		{
			super(state, "You have no maximum hand size.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.SET_MAX_HAND_SIZE);
			part.parameters.put(ContinuousEffectType.Parameter.PLAYER, ControllerOf.instance(This.instance()));
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Empty.instance());
			this.addEffectPart(part);
		}
	}

	public static final class VensersJournalAbility1 extends EventTriggeredAbility
	{
		public VensersJournalAbility1(GameState state)
		{
			super(state, "At the beginning of your upkeep, you gain 1 life for each card in your hand.");
			this.addPattern(atTheBeginningOfYourUpkeep());
			this.addEffect(gainLife(You.instance(), Count.instance(InZone.instance(HandOf.instance(You.instance()))), "You gain 1 life for each card in your hand."));
		}
	}

	public VensersJournal(GameState state)
	{
		super(state);

		// You have no maximum hand size.
		this.addAbility(new SpellbookAbility(state));

		// At the beginning of your upkeep, you gain 1 life for each card in
		// your hand.
		this.addAbility(new VensersJournalAbility1(state));
	}
}
