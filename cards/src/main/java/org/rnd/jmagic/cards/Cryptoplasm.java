package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Cryptoplasm")
@Types({Type.CREATURE})
@SubTypes({SubType.SHAPESHIFTER})
@ManaCost("1UU")
@Printings({@Printings.Printed(ex = MirrodinBesieged.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class Cryptoplasm extends Card
{
	public static final class CryptoplasmAbility0 extends EventTriggeredAbility
	{
		public CryptoplasmAbility0(GameState state)
		{
			super(state, "At the beginning of your upkeep, you may have Cryptoplasm become a copy of another target creature. If you do, Cryptoplasm gains this ability.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			SetGenerator target = targetedBy(this.addTarget(RelativeComplement.instance(CreaturePermanents.instance(), ABILITY_SOURCE_OF_THIS), "another target creature"));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.COPY_OBJECT);
			part.parameters.put(ContinuousEffectType.Parameter.ABILITY, Identity.instance(new SimpleAbilityFactory(CryptoplasmAbility0.class)));
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
			part.parameters.put(ContinuousEffectType.Parameter.ORIGINAL, target);

			EventFactory copy = createFloatingEffect(Empty.instance(), "Cryptoplasm becomes a copy of another target creature and gains this ability.", part);
			this.addEffect(youMay(copy, "You may have Cryptoplasm become a copy of another target creature. If you do, Cryptoplasm gains this ability."));
		}
	}

	public Cryptoplasm(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// At the beginning of your upkeep, you may have Cryptoplasm become a
		// copy of another target creature. If you do, Cryptoplasm gains this
		// ability.
		this.addAbility(new CryptoplasmAbility0(state));
	}
}
