package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Elvish Piper")
@Types({Type.CREATURE})
@SubTypes({SubType.SHAMAN, SubType.ELF})
@ManaCost("3G")
@Printings({@Printings.Printed(ex = Magic2010.class, r = Rarity.RARE), @Printings.Printed(ex = TenthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = NinthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = EighthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = SeventhEdition.class, r = Rarity.RARE), @Printings.Printed(ex = UrzasDestiny.class, r = Rarity.RARE)})
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
