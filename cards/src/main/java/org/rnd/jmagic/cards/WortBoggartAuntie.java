package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Wort, Boggart Auntie")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.SHAMAN, SubType.GOBLIN})
@ManaCost("2BR")
@Printings({@Printings.Printed(ex = Lorwyn.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK, Color.RED})
public final class WortBoggartAuntie extends Card
{
	public static final class AuntiesBlackMagic extends EventTriggeredAbility
	{
		public AuntiesBlackMagic(GameState state)
		{
			super(state, "At the beginning of your upkeep, you may return target Goblin card from your graveyard to your hand.");

			this.addPattern(atTheBeginningOfYourUpkeep());

			Target target = this.addTarget(Intersect.instance(HasSubType.instance(SubType.GOBLIN), InZone.instance(GraveyardOf.instance(You.instance()))), "target Goblin card from your graveyard");

			EventFactory returnFactory = new EventFactory(EventType.MOVE_OBJECTS, "Return target Goblin card from your graveyard to your hand.");
			returnFactory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			returnFactory.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
			returnFactory.parameters.put(EventType.Parameter.OBJECT, targetedBy(target));

			EventFactory mayFactory = new EventFactory(EventType.PLAYER_MAY, "You may return target Goblin card from your graveyard to your hand.");
			mayFactory.parameters.put(EventType.Parameter.PLAYER, You.instance());
			mayFactory.parameters.put(EventType.Parameter.EVENT, Identity.instance(returnFactory));
			this.addEffect(mayFactory);
		}
	}

	public WortBoggartAuntie(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Fear(state));

		this.addAbility(new AuntiesBlackMagic(state));
	}
}
