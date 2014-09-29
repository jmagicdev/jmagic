package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sarkhan, the Dragonspeaker")
@Types({Type.PLANESWALKER})
@SubTypes({SubType.SARKHAN})
@ManaCost("3RR")
@ColorIdentity({Color.RED})
public final class SarkhantheDragonspeaker extends Card
{
	public static final class SarkhantheDragonspeakerAbility0 extends LoyaltyAbility
	{
		public SarkhantheDragonspeakerAbility0(GameState state)
		{
			super(state, +1, "Until end of turn, Sarkhan, the Dragonspeaker becomes a legendary 4/4 red Dragon creature with flying, indestructible, and haste.");

			Animator dragon = new Animator(ABILITY_SOURCE_OF_THIS, 4, 4);
			dragon.addSuperType(SuperType.LEGENDARY);
			dragon.addColor(Color.RED);
			dragon.addSubType(SubType.DRAGON);
			dragon.removeOldTypes();
			dragon.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
			dragon.addAbility(org.rnd.jmagic.abilities.keywords.Indestructible.class);
			dragon.addAbility(org.rnd.jmagic.abilities.keywords.Haste.class);
			this.addEffect(createFloatingEffect("Until end of turn, Sarkhan, the Dragonspeaker becomes a legendary 4/4 red Dragon creature with flying, indestructible, and haste.", dragon.getParts()));
		}
	}

	public static final class SarkhantheDragonspeakerAbility1 extends LoyaltyAbility
	{
		public SarkhantheDragonspeakerAbility1(GameState state)
		{
			super(state, -3, "Sarkhan, the Dragonspeaker deals 4 damage to target creature.");
			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(permanentDealDamage(4, target, "Sarkhan, the Dragonspeaker deals 4 damage to target creature."));
		}
	}

	public static final class DoubleHowlingMine extends EventTriggeredAbility
	{
		public DoubleHowlingMine(GameState state)
		{
			super(state, "At the beginning of your draw step, draw two additional cards.");
			this.addPattern(atTheBeginningOfYourDrawStep());
			this.addEffect(drawCards(You.instance(), 2, "Draw two additional cards."));
		}
	}

	public static final class ReverseHowlingMine extends EventTriggeredAbility
	{
		public ReverseHowlingMine(GameState state)
		{
			super(state, "At the beginning of your end step, discard your hand.");
			this.addPattern(atTheBeginningOfYourEndStep());
			this.addEffect(discardHand(You.instance(), "Discard your hand."));
		}
	}

	public static final class SarkhantheDragonspeakerAbility2 extends LoyaltyAbility
	{
		public SarkhantheDragonspeakerAbility2(GameState state)
		{
			super(state, -6, "You get an emblem with \"At the beginning of your draw step, draw two additional cards\" and \"At the beginning of your end step, discard your hand.\"");

			EventFactory emblem = new EventFactory(EventType.CREATE_EMBLEM, "You get an emblem with \"At the beginning of your draw step, draw two additional cards\" and \"At the beginning of your end step, discard your hand.\"");
			emblem.parameters.put(EventType.Parameter.CAUSE, This.instance());
			emblem.parameters.put(EventType.Parameter.ABILITY, Identity.instance(DoubleHowlingMine.class, ReverseHowlingMine.class));
			emblem.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			this.addEffect(emblem);
		}
	}

	public SarkhantheDragonspeaker(GameState state)
	{
		super(state);

		this.setPrintedLoyalty(4);

		// +1: Until end of turn, Sarkhan, the Dragonspeaker becomes a legendary
		// 4/4 red Dragon creature with flying, indestructible, and haste. (He
		// doesn't lose loyalty while he's not a planeswalker.)
		this.addAbility(new SarkhantheDragonspeakerAbility0(state));

		// -3: Sarkhan, the Dragonspeaker deals 4 damage to target creature.
		this.addAbility(new SarkhantheDragonspeakerAbility1(state));

		// -6: You get an emblem with
		// "At the beginning of your draw step, draw two additional cards" and
		// "At the beginning of your end step, discard your hand."
		this.addAbility(new SarkhantheDragonspeakerAbility2(state));
	}
}
