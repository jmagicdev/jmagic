package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Elvish Piper")
@Types({Type.CREATURE})
@SubTypes({SubType.SHAMAN, SubType.ELF})
@ManaCost("3G")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.RARE), @Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.EIGHTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.SEVENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.URZAS_DESTINY, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class ElvishPiper extends Card
{
	public static final class Pipe extends ActivatedAbility
	{
		public Pipe(GameState state)
		{
			super(state, "(G), (T): You may put a creature card from your hand onto the battlefield.");

			this.setManaCost(new ManaPool("G"));

			this.costsTap = true;

			SetGenerator inYourHand = InZone.instance(HandOf.instance(You.instance()));

			EventFactory putOntoBattlefield = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD_CHOICE, "Put a creature card from your hand onto the battlefield");
			putOntoBattlefield.parameters.put(EventType.Parameter.CAUSE, This.instance());
			putOntoBattlefield.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			putOntoBattlefield.parameters.put(EventType.Parameter.OBJECT, Intersect.instance(inYourHand, HasType.instance(Type.CREATURE)));

			this.addEffect(youMay(putOntoBattlefield, "You may put a creature card from your hand onto the battlefield."));
		}
	}

	public ElvishPiper(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new Pipe(state));
	}
}
