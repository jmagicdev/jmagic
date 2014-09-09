package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Crypt Ghast")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("3B")
@ColorIdentity({Color.BLACK})
public final class CryptGhast extends Card
{
	public static final class CryptGhastAbility1 extends EventTriggeredAbility
	{
		public CryptGhastAbility1(GameState state)
		{
			super(state, "Whenever you tap a Swamp for mana, add (B) to your mana pool.");

			this.addPattern(tappedForMana(You.instance(), landPermanents(SubType.SWAMP)));

			EventFactory addMana = new EventFactory(EventType.ADD_MANA, "Add (B) to your mana pool.");
			addMana.parameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
			addMana.parameters.put(EventType.Parameter.MANA, Identity.fromCollection(new ManaPool("B")));
			addMana.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(addMana);
		}
	}

	public CryptGhast(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Extort (Whenever you cast a spell, you may pay (w/b). If you do, each
		// opponent loses 1 life and you gain that much life.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Extort(state));

		// Whenever you tap a Swamp for mana, add (B) to your mana pool (in
		// addition to the mana the land produces).
		this.addAbility(new CryptGhastAbility1(state));
	}
}
