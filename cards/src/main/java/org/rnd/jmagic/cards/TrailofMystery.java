package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Trail of Mystery")
@Types({Type.ENCHANTMENT})
@ManaCost("1G")
@ColorIdentity({Color.GREEN})
public final class TrailofMystery extends Card
{
	public static final class TrailofMysteryAbility0 extends EventTriggeredAbility
	{
		public TrailofMysteryAbility0(GameState state)
		{
			super(state, "Whenever a face-down creature enters the battlefield under your control, you may search your library for a basic land card, reveal it, put it into your hand, then shuffle your library.");

			SetGenerator morphs = Intersect.instance(FaceDown.instance(), HasType.instance(Type.CREATURE));
			SimpleZoneChangePattern playMorph = new SimpleZoneChangePattern(null, Battlefield.instance(), morphs, You.instance(), false);
			this.addPattern(playMorph);

			this.addEffect(youMay(searchYourLibraryForABasicLandCardAndPutItIntoYourHand()));
		}
	}

	public static final class TrailofMysteryAbility1 extends EventTriggeredAbility
	{
		public TrailofMysteryAbility1(GameState state)
		{
			super(state, "Whenever a permanent you control is turned face up, if it's a creature, it gets +2/+2 until end of turn.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.TURN_PERMANENT_FACE_UP);
			pattern.put(EventType.Parameter.OBJECT, ControlledBy.instance(You.instance()));
			this.addPattern(pattern);

			SetGenerator thatPermanent = EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.OBJECT);
			this.interveningIf = Intersect.instance(thatPermanent, HasType.instance(Type.CREATURE));

			this.addEffect(ptChangeUntilEndOfTurn(thatPermanent, +2, +2, "That creature gets +2/+2 until end of turn."));
		}
	}

	public TrailofMystery(GameState state)
	{
		super(state);

		// Whenever a face-down creature enters the battlefield under your
		// control, you may search your library for a basic land card, reveal
		// it, put it into your hand, then shuffle your library.
		this.addAbility(new TrailofMysteryAbility0(state));

		// Whenever a permanent you control is turned face up, if it's a
		// creature, it gets +2/+2 until end of turn.
		this.addAbility(new TrailofMysteryAbility1(state));
	}
}
