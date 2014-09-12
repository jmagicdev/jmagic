package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Garruk, Apex Predator")
@Types({Type.PLANESWALKER})
@SubTypes({SubType.GARRUK})
@ManaCost("5BG")
@ColorIdentity({Color.BLACK, Color.GREEN})
public final class GarrukApexPredator extends Card
{
	public static final class GarrukApexPredatorAbility0 extends LoyaltyAbility
	{
		public GarrukApexPredatorAbility0(GameState state)
		{
			super(state, +1, "Destroy another target planeswalker.");
			SetGenerator planeswalkers = Intersect.instance(HasType.instance(Type.PLANESWALKER), Permanents.instance());
			SetGenerator otherPlaneswalker = RelativeComplement.instance(planeswalkers, ABILITY_SOURCE_OF_THIS);
			SetGenerator target = targetedBy(this.addTarget(otherPlaneswalker, "another target planeswalker"));
			this.addEffect(destroy(target, "Destroy another target planeswalker."));
		}
	}

	public static final class GarrukApexPredatorAbility1 extends LoyaltyAbility
	{
		public GarrukApexPredatorAbility1(GameState state)
		{
			super(state, +1, "Put a 3/3 black Beast creature token with deathtouch onto the battlefield.");

			CreateTokensFactory beast = new CreateTokensFactory(1, 3, 3, "Put a 3/3 black Beast creature token with deathtouch onto the battlefield.");
			beast.setColors(Color.BLACK);
			beast.setSubTypes(SubType.BEAST);
			beast.addAbility(org.rnd.jmagic.abilities.keywords.Deathtouch.class);
			this.addEffect(beast.getEventFactory());
		}
	}

	public static final class GarrukApexPredatorAbility2 extends LoyaltyAbility
	{
		public GarrukApexPredatorAbility2(GameState state)
		{
			super(state, -3, "Destroy target creature. You gain life equal to its toughness.");
			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(destroy(target, "Destroy target creature."));
			this.addEffect(gainLife(You.instance(), ToughnessOf.instance(target), "You gain life equal to its toughness."));
		}
	}

	public static final class YouGuysMustReallyHateMe extends EventTriggeredAbility
	{
		public YouGuysMustReallyHateMe(GameState state)
		{
			super(state, "Whenever a creature attacks you, it gets +5/+5 and gains trample until end of turn.");

			SimpleEventPattern attacksYou = new SimpleEventPattern(EventType.DECLARE_ONE_ATTACKER);
			attacksYou.put(EventType.Parameter.DEFENDER, You.instance());
			this.addPattern(attacksYou);

			SetGenerator it = EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.OBJECT);
			this.addEffect(ptChangeAndAbilityUntilEndOfTurn(it, +5, +5, "That creature gets +5/+5 and gains trample until end of turn.", org.rnd.jmagic.abilities.keywords.Trample.class));
		}
	}

	public static final class GarrukApexPredatorAbility3 extends LoyaltyAbility
	{
		public GarrukApexPredatorAbility3(GameState state)
		{
			super(state, -8, "Target opponent gets an emblem with \"Whenever a creature attacks you, it gets +5/+5 and gains trample until end of turn.\"");

			SetGenerator target = targetedBy(this.addTarget(OpponentsOf.instance(You.instance()), "target opponent"));

			EventFactory emblem = new EventFactory(EventType.CREATE_EMBLEM, "Target opponent gets an emblem with \"Whenever a creature attacks you, it gets +5/+5 and gains trample until end of turn.\"");
			emblem.parameters.put(EventType.Parameter.CAUSE, This.instance());
			emblem.parameters.put(EventType.Parameter.ABILITY, Identity.instance(YouGuysMustReallyHateMe.class));
			emblem.parameters.put(EventType.Parameter.CONTROLLER, target);
			this.addEffect(emblem);
		}
	}

	public GarrukApexPredator(GameState state)
	{
		super(state);

		this.setPrintedLoyalty(5);

		// +1: Destroy another target planeswalker.
		this.addAbility(new GarrukApexPredatorAbility0(state));

		// +1: Put a 3/3 black Beast creature token with deathtouch onto the
		// battlefield.
		this.addAbility(new GarrukApexPredatorAbility1(state));

		// -3: Destroy target creature. You gain life equal to its toughness.
		this.addAbility(new GarrukApexPredatorAbility2(state));

		// -8: Target opponent gets an emblem with
		// "Whenever a creature attacks you, it gets +5/+5 and gains trample until end of turn."
		this.addAbility(new GarrukApexPredatorAbility3(state));
	}
}
