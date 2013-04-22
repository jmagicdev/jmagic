package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Anarchist")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.HUMAN})
@ManaCost("4R")
@Printings({@Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.ODYSSEY, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.EXODUS, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class Anarchist extends Card
{
	public static final class AnarchistAbility0 extends EventTriggeredAbility
	{
		public AnarchistAbility0(GameState state)
		{
			super(state, "When Anarchist enters the battlefield, you may return target sorcery card from your graveyard to your hand.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator target = targetedBy(this.addTarget(Intersect.instance(HasType.instance(Type.SORCERY), InZone.instance(GraveyardOf.instance(You.instance()))), "target sorcery card in your graveyard"));

			EventFactory factory = new EventFactory(EventType.MOVE_OBJECTS, "Return target sorcery card from your graveyard to your hand.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
			factory.parameters.put(EventType.Parameter.OBJECT, target);
			this.addEffect(factory);
		}
	}

	public Anarchist(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// When Anarchist enters the battlefield, you may return target sorcery
		// card from your graveyard to your hand.
		this.addAbility(new AnarchistAbility0(state));
	}
}
