package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Helvault")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.ARTIFACT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.MYTHIC)})
@ColorIdentity({})
public final class Helvault extends Card
{
	public static final class HelvaultAbility0 extends ActivatedAbility
	{
		public HelvaultAbility0(GameState state)
		{
			super(state, "(1), (T): Exile target creature you control.");
			this.setManaCost(new ManaPool("(1)"));
			this.costsTap = true;
			this.getLinkManager().addLinkClass(HelvaultAbility2.class);

			SetGenerator target = targetedBy(this.addTarget(CREATURES_YOU_CONTROL, "target creature you control"));
			EventFactory exile = exile(target, "Exile target creature you control.");
			exile.setLink(this);
			this.addEffect(exile);
		}
	}

	public static final class HelvaultAbility1 extends ActivatedAbility
	{
		public HelvaultAbility1(GameState state)
		{
			super(state, "(7), (T): Exile target creature you don't control.");
			this.setManaCost(new ManaPool("(7)"));
			this.costsTap = true;
			this.getLinkManager().addLinkClass(HelvaultAbility2.class);

			SetGenerator creatures = RelativeComplement.instance(CreaturePermanents.instance(), CREATURES_YOU_CONTROL);
			SetGenerator target = targetedBy(this.addTarget(creatures, "target creature you don't control"));
			EventFactory exile = exile(target, "Exile target creature you don't control.");
			exile.setLink(this);
			this.addEffect(exile);
		}
	}

	public static final class HelvaultAbility2 extends EventTriggeredAbility
	{
		public HelvaultAbility2(GameState state)
		{
			super(state, "When Helvault is put into a graveyard from the battlefield, return all cards exiled with it to the battlefield under their owners' control.");
			this.addPattern(whenThisIsPutIntoAGraveyardFromTheBattlefield());
			this.getLinkManager().addLinkClass(HelvaultAbility0.class);
			this.getLinkManager().addLinkClass(HelvaultAbility1.class);

			EventFactory put = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD_UNDER_OWNER_CONTROL, "Return all cards exiled with it to the battlefield under their owners' control.");
			put.parameters.put(EventType.Parameter.CAUSE, This.instance());
			put.parameters.put(EventType.Parameter.OBJECT, ChosenFor.instance(LinkedTo.instance(This.instance())));
			this.addEffect(put);
		}
	}

	public Helvault(GameState state)
	{
		super(state);

		// (1), (T): Exile target creature you control.
		this.addAbility(new HelvaultAbility0(state));

		// (7), (T): Exile target creature you don't control.
		this.addAbility(new HelvaultAbility1(state));

		// When Helvault is put into a graveyard from the battlefield, return
		// all cards exiled with it to the battlefield under their owners'
		// control.
		this.addAbility(new HelvaultAbility2(state));
	}
}
