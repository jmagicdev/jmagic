package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Stoneforge Mystic")
@Types({Type.CREATURE})
@SubTypes({SubType.ARTIFICER, SubType.KOR})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class StoneforgeMystic extends Card
{
	public static final class GetEquipment extends EventTriggeredAbility
	{
		public GetEquipment(GameState state)
		{
			super(state, "When Stoneforge Mystic enters the battlefield, you may search your library for an Equipment card, reveal it, put it into your hand, then shuffle your library.");
			this.addPattern(whenThisEntersTheBattlefield());

			EventFactory get = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for an Equipment card, reveal it, put it into your hand, then shuffle your library");
			get.parameters.put(EventType.Parameter.CAUSE, This.instance());
			get.parameters.put(EventType.Parameter.PLAYER, You.instance());
			get.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			get.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
			get.parameters.put(EventType.Parameter.TYPE, Identity.instance(HasSubType.instance(SubType.EQUIPMENT)));
			this.addEffect(youMay(get, "You may search your library for an Equipment card, reveal it, put it into your hand, then shuffle your library."));
		}
	}

	public static final class DropEquipment extends ActivatedAbility
	{
		public DropEquipment(GameState state)
		{
			super(state, "(1)(W), (T): You may put an Equipment card from your hand onto the battlefield.");
			this.setManaCost(new ManaPool("1W"));
			this.costsTap = true;

			SetGenerator equipmentInYourHand = Intersect.instance(HasSubType.instance(SubType.EQUIPMENT), InZone.instance(HandOf.instance(You.instance())));

			EventFactory drop = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD_CHOICE, "Put an Equipment card from your hand onto the battlefield");
			drop.parameters.put(EventType.Parameter.CAUSE, This.instance());
			drop.parameters.put(EventType.Parameter.CAUSE, This.instance());
			drop.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			drop.parameters.put(EventType.Parameter.OBJECT, equipmentInYourHand);
			this.addEffect(youMay(drop, "You may put an Equipment card from your hand onto the battlefield."));
		}
	}

	public StoneforgeMystic(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(2);

		// When Stoneforge Mystic enters the battlefield, you may search your
		// library for an Equipment card, reveal it, put it into your hand, then
		// shuffle your library.
		this.addAbility(new GetEquipment(state));

		// (1)(W), (T): You may put an Equipment card from your hand onto the
		// battlefield.
		this.addAbility(new DropEquipment(state));
	}
}
