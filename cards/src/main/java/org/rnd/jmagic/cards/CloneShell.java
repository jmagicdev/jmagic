package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Clone Shell")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.SHAPESHIFTER})
@ManaCost("5")
@Printings({@Printings.Printed(ex = ScarsOfMirrodin.class, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class CloneShell extends Card
{
	public static final class CloneShellAbility0 extends EventTriggeredAbility
	{
		public CloneShellAbility0(GameState state)
		{
			super(state, "When Clone Shell enters the battlefield, look at the top four cards of your library, exile one face down, then put the rest on the bottom of your library in any order.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator yourLibrary = LibraryOf.instance(You.instance());
			SetGenerator topFour = TopCards.instance(4, yourLibrary);

			EventFactory look = new EventFactory(EventType.LOOK, "Look at the top four cards of your library,");
			look.parameters.put(EventType.Parameter.CAUSE, This.instance());
			look.parameters.put(EventType.Parameter.PLAYER, You.instance());
			look.parameters.put(EventType.Parameter.OBJECT, topFour);
			this.addEffect(look);

			EventFactory factory = new EventFactory(EventType.MOVE_CHOICE, "exile one face down,");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			factory.parameters.put(EventType.Parameter.OBJECT, topFour);
			factory.parameters.put(EventType.Parameter.CHOICE, Identity.instance(PlayerInterface.ChooseReason.EXILE));
			factory.parameters.put(EventType.Parameter.TO, ExileZone.instance());
			factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
			factory.parameters.put(EventType.Parameter.HIDDEN, NonEmpty.instance());

			EventFactory exile = factory;
			exile.setLink(this);
			this.addEffect(exile);

			SetGenerator theRest = RelativeComplement.instance(EffectResult.instance(look), OldObjectOf.instance(EffectResult.instance(exile)));

			EventFactory bottom = new EventFactory(EventType.PUT_INTO_LIBRARY, "then put the rest on the bottom of your library in any order.");
			bottom.parameters.put(EventType.Parameter.CAUSE, This.instance());
			bottom.parameters.put(EventType.Parameter.INDEX, numberGenerator(-1));
			bottom.parameters.put(EventType.Parameter.OBJECT, theRest);
			this.addEffect(bottom);

			this.getLinkManager().addLinkClass(CloneShellAbility1.class);
		}
	}

	public static final class CloneShellAbility1 extends EventTriggeredAbility
	{
		public CloneShellAbility1(GameState state)
		{
			super(state, "When Clone Shell dies, turn the exiled card face up. If it's a creature card, put it onto the battlefield under your control.");
			this.addPattern(whenThisDies());

			this.getLinkManager().addLinkClass(CloneShellAbility0.class);
			SetGenerator imprintedCard = ChosenFor.instance(LinkedTo.instance(This.instance()));

			EventFactory show = new EventFactory(EventType.SHOW, "Turn the exiled card face up.");
			show.parameters.put(EventType.Parameter.OBJECT, imprintedCard);
			this.addEffect(show);

			EventFactory get = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD, "Put it onto the battlefield under your control");
			get.parameters.put(EventType.Parameter.CAUSE, This.instance());
			get.parameters.put(EventType.Parameter.OBJECT, imprintedCard);
			get.parameters.put(EventType.Parameter.CONTROLLER, You.instance());

			EventFactory effect = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "If it's a creature card, put it onto the battlefield under your control.");
			effect.parameters.put(EventType.Parameter.IF, Intersect.instance(imprintedCard, HasType.instance(Type.CREATURE)));
			effect.parameters.put(EventType.Parameter.THEN, Identity.instance(get));
			this.addEffect(effect);
		}
	}

	public CloneShell(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Imprint \u2014 When Clone Shell enters the battlefield, look at the
		// top four cards of your library, exile one face down, then put the
		// rest on the bottom of your library in any order.
		this.addAbility(new CloneShellAbility0(state));

		// When Clone Shell is put into a graveyard from the battlefield, turn
		// the exiled card face up. If it's a creature card, put it onto the
		// battlefield under your control.
		this.addAbility(new CloneShellAbility1(state));
	}
}
