package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Corpse Cur")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.HOUND})
@ManaCost("4")
@Printings({@Printings.Printed(ex = ScarsOfMirrodin.class, r = Rarity.COMMON)})
@ColorIdentity({})
public final class CorpseCur extends Card
{
	public static final class CorpseCurAbility1 extends EventTriggeredAbility
	{
		public CorpseCurAbility1(GameState state)
		{
			super(state, "When Corpse Cur enters the battlefield, you may return target creature card with infect from your graveyard to your hand.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator yourGraveyard = GraveyardOf.instance(You.instance());
			SetGenerator target = targetedBy(this.addTarget(Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(yourGraveyard), HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Infect.class)), "target creature card with infect in your graveyard"));

			EventFactory factory = new EventFactory(EventType.MOVE_OBJECTS, "Return target creature card with infect from your graveyard to your hand.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
			factory.parameters.put(EventType.Parameter.OBJECT, target);
			this.addEffect(factory);
		}
	}

	public CorpseCur(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Infect (This creature deals damage to creatures in the form of -1/-1
		// counters and to players in the form of poison counters.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Infect(state));

		// When Corpse Cur enters the battlefield, you may return target
		// creature card with infect from your graveyard to your hand.
		this.addAbility(new CorpseCurAbility1(state));
	}
}
