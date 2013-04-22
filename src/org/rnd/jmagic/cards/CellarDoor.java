package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Cellar Door")
@Types({Type.ARTIFACT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class CellarDoor extends Card
{
	public static final class CellarDoorAbility0 extends ActivatedAbility
	{
		public CellarDoorAbility0(GameState state)
		{
			super(state, "(3), (T): Target player puts the bottom card of his or her library into his or her graveyard. If it's a creature card, you put a 2/2 black Zombie creature token onto the battlefield.");
			this.setManaCost(new ManaPool("(3)"));
			this.costsTap = true;

			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
			SetGenerator library = LibraryOf.instance(target);

			EventFactory putIntoGraveyard = new EventFactory(EventType.MOVE_OBJECTS, "Target player puts the bottom card of his or her library into his or her graveyard.");
			putIntoGraveyard.parameters.put(EventType.Parameter.CAUSE, This.instance());
			putIntoGraveyard.parameters.put(EventType.Parameter.TO, GraveyardOf.instance(target));
			putIntoGraveyard.parameters.put(EventType.Parameter.OBJECT, BottomCards.instance(1, library));
			this.addEffect(putIntoGraveyard);

			SetGenerator object = NewObjectOf.instance(EffectResult.instance(putIntoGraveyard));
			SetGenerator isCreature = Intersect.instance(object, HasType.instance(Type.CREATURE));

			CreateTokensFactory token = new CreateTokensFactory(1, 2, 2, "you put a 2/2 black Zombie creature token onto the battlefield.");
			token.setColors(Color.BLACK);
			token.setSubTypes(SubType.ZOMBIE);

			this.addEffect(ifThen(isCreature, token.getEventFactory(), "If it's a creature card, you put a 2/2 black Zombie creature token onto the battlefield."));
		}
	}

	public CellarDoor(GameState state)
	{
		super(state);

		// (3), (T): Target player puts the bottom card of his or her library
		// into his or her graveyard. If it's a creature card, you put a 2/2
		// black Zombie creature token onto the battlefield.
		this.addAbility(new CellarDoorAbility0(state));
	}
}
