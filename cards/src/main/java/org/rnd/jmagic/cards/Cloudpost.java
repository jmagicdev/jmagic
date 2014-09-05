package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Cloudpost")
@Types({Type.LAND})
@SubTypes({SubType.LOCUS})
@Printings({@Printings.Printed(ex = Mirrodin.class, r = Rarity.COMMON)})
@ColorIdentity({})
public final class Cloudpost extends Card
{
	public static final class CloudpostAbility1 extends ActivatedAbility
	{
		public CloudpostAbility1(GameState state)
		{
			super(state, "(T): Add (1) to your mana pool for each Locus on the battlefield.");
			this.costsTap = true;

			SetGenerator locus = HasSubType.instance(SubType.LOCUS);
			SetGenerator locusOnTheBattlefield = Intersect.instance(locus, Permanents.instance());
			SetGenerator forEachLocusOnTheBattlefield = Count.instance(locusOnTheBattlefield);

			EventFactory addMana = new EventFactory(EventType.ADD_MANA, "Add (1) to your mana pool for each Locus on the battlefield.");
			addMana.parameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
			addMana.parameters.put(EventType.Parameter.MANA, Identity.fromCollection(new ManaPool("1")));
			addMana.parameters.put(EventType.Parameter.NUMBER, forEachLocusOnTheBattlefield);
			addMana.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(addMana);
		}
	}

	public Cloudpost(GameState state)
	{
		super(state);

		// Cloudpost enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		// (T): Add (1) to your mana pool for each Locus on the battlefield.
		this.addAbility(new CloudpostAbility1(state));
	}
}
