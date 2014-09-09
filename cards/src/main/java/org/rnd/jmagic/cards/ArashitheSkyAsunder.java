package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Arashi, the Sky Asunder")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("3GG")
@ColorIdentity({Color.GREEN})
public final class ArashitheSkyAsunder extends Card
{
	public static final class ArashitheSkyAsunderAbility0 extends ActivatedAbility
	{
		public ArashitheSkyAsunderAbility0(GameState state)
		{
			super(state, "(X)(G), (T): Arashi, the Sky Asunder deals X damage to target creature with flying.");
			this.setManaCost(new ManaPool("(X)(G)"));
			this.costsTap = true;

			SetGenerator flying = HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class);
			SetGenerator creaturesWithFlying = Intersect.instance(CreaturePermanents.instance(), flying);
			this.addEffect(permanentDealDamage(ValueOfX.instance(This.instance()), creaturesWithFlying, "Arashi, the Sky Asunder deals X damage to target creature with flying."));
		}
	}

	public static final class ArashitheSkyAsunderAbility1 extends ActivatedAbility
	{
		public ArashitheSkyAsunderAbility1(GameState state)
		{
			super(state, "Channel \u2014 (X)(G)(G), Discard Arashi: Arashi deals X damage to each creature with flying.");
			// Channel \u2014 (X)(G)(G)
			this.setManaCost(new ManaPool("(X)(G)(G)"));

			// Discard Arashi
			EventFactory discard = new EventFactory(EventType.DISCARD_CARDS, "Discard Arashi");
			discard.parameters.put(EventType.Parameter.CAUSE, This.instance());
			discard.parameters.put(EventType.Parameter.CARD, ABILITY_SOURCE_OF_THIS);
			discard.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addCost(discard);

			SetGenerator flying = HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class);
			SetGenerator creaturesWithFlying = Intersect.instance(CreaturePermanents.instance(), flying);
			this.addEffect(permanentDealDamage(ValueOfX.instance(This.instance()), creaturesWithFlying, "Arashi, the Sky Asunder deals X damage to target creature with flying."));
		}
	}

	public ArashitheSkyAsunder(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// (X)(G), (T): Arashi, the Sky Asunder deals X damage to target
		// creature with flying.
		this.addAbility(new ArashitheSkyAsunderAbility0(state));

		// Channel \u2014 (X)(G)(G), Discard Arashi: Arashi deals X damage to
		// each creature with flying.
		this.addAbility(new ArashitheSkyAsunderAbility1(state));
	}
}
