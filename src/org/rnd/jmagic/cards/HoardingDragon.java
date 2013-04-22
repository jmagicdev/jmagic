package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Hoarding Dragon")
@Types({Type.CREATURE})
@SubTypes({SubType.DRAGON})
@ManaCost("3RR")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class HoardingDragon extends Card
{
	public static final class HoardingDragonAbility1 extends EventTriggeredAbility
	{
		public HoardingDragonAbility1(GameState state)
		{
			super(state, "When Hoarding Dragon enters the battlefield, you may search your library for an artifact card, exile it, then shuffle your library.");
			this.addPattern(whenThisEntersTheBattlefield());

			EventFactory effect = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for an artifact card, exile it, then shuffle your library.");
			effect.parameters.put(EventType.Parameter.CAUSE, This.instance());
			effect.parameters.put(EventType.Parameter.PLAYER, You.instance());
			effect.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			effect.parameters.put(EventType.Parameter.TO, ExileZone.instance());
			effect.parameters.put(EventType.Parameter.TYPE, Identity.instance(HasType.instance(Type.ARTIFACT)));
			this.addEffect(youMay(effect, "You may search your library for an artifact card, exile it, then shuffle your library."));
			effect.setLink(this);

			this.getLinkManager().addLinkClass(HoardingDragonAbility2.class);
		}
	}

	public static final class HoardingDragonAbility2 extends EventTriggeredAbility
	{
		public HoardingDragonAbility2(GameState state)
		{
			super(state, "When Hoarding Dragon dies, you may put the exiled card into its owner's hand.");
			this.addPattern(whenThisDies());

			this.getLinkManager().addLinkClass(HoardingDragonAbility1.class);
			SetGenerator exiledCard = ChosenFor.instance(LinkedTo.instance(This.instance()));

			EventFactory effect = new EventFactory(EventType.MOVE_OBJECTS, "Put the exiled card into its owner's hand.");
			effect.parameters.put(EventType.Parameter.CAUSE, This.instance());
			effect.parameters.put(EventType.Parameter.TO, HandOf.instance(OwnerOf.instance(exiledCard)));
			effect.parameters.put(EventType.Parameter.OBJECT, exiledCard);
			this.addEffect(youMay(effect, "You may put the exiled card into its owner's hand."));
		}
	}

	public HoardingDragon(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Hoarding Dragon enters the battlefield, you may search your
		// library for an artifact card, exile it, then shuffle your library.
		this.addAbility(new HoardingDragonAbility1(state));

		// When Hoarding Dragon is put into a graveyard from the battlefield,
		// you may put the exiled card into its owner's hand.
		this.addAbility(new HoardingDragonAbility2(state));
	}
}
