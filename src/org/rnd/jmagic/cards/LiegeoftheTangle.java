package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Liege of the Tangle")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("6GG")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.MYTHIC)})
@ColorIdentity({Color.GREEN})
public final class LiegeoftheTangle extends Card
{
	public static final class LiegeoftheTangleAbility1 extends EventTriggeredAbility
	{
		public LiegeoftheTangleAbility1(GameState state)
		{
			super(state, "Whenever Liege of the Tangle deals combat damage to a player, you may choose any number of target lands you control and put an awakening counter on each of them. Each of those lands is an 8/8 green Elemental creature for as long as it has an awakening counter on it. They're still lands.");
			this.addPattern(whenThisDealsCombatDamageToAPlayer());

			Target targetLands = this.addTarget(Intersect.instance(LandPermanents.instance(), ControlledBy.instance(You.instance())), "any number of target lands you control");
			targetLands.setNumber(0, null);

			this.addEffect(putCounters(1, Counter.CounterType.AWAKENING, targetedBy(targetLands), "Put an awakening counter on any number of target lands you control."));

			Animator awaken = new Animator(targetedBy(targetLands), 8, 8);
			awaken.addColor(Color.GREEN);
			awaken.addSubType(SubType.ELEMENTAL);
			this.addEffect(createFloatingEffect("Each of those lands is an 8/8 green Elemental creature for as long as it has an awakening counter on it. They're still lands.", awaken.getParts()));
		}
	}

	public LiegeoftheTangle(GameState state)
	{
		super(state);

		this.setPower(8);
		this.setToughness(8);

		// Trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));

		// Whenever Liege of the Tangle deals combat damage to a player, you may
		// choose any number of target lands you control and put an awakening
		// counter on each of them. Each of those lands is an 8/8 green
		// Elemental creature for as long as it has an awakening counter on it.
		// They're still lands.
		this.addAbility(new LiegeoftheTangleAbility1(state));
	}
}
